package com.lord.arbam.services_impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.exceptions.NegativeNumberException;
import com.lord.arbam.models.ProductCategory;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductPrice;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.repositories.ProductCategoryRepository;
import com.lord.arbam.repositories.ProductPriceRepository;
import com.lord.arbam.repositories.ProductRepository;
import com.lord.arbam.services.IngredientService;
import com.lord.arbam.services.ProductService;
import com.lord.arbam.services.ProductStockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final ProductCategoryRepository productCategoryRepository;

	@Autowired
	private final ProductPriceRepository productPriceRepository;

	@Autowired
	private final ProductStockService productStockService;

	@Autowired
	private final IngredientService ingredientService;

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public Product findProductById(Long id) {
		return productRepository.findById(id).orElseThrow(
				() -> new ItemNotFoundException("No se encontro el producto. ProductServiceImpl.findProductById"));
	}

	@Override
	public Product saveProduct(Product product) {
		log.info("Creando nuevo producto");
		ProductCategory category = productCategoryRepository.findById(product.getCategory()
				.getId()).orElseThrow(()-> new ItemNotFoundException("No se encontro la categoria"));
				
		ProductPrice price = productPriceRepository.save(new ProductPrice(product.getProductPrice().getPrice()));

		Product newProduct = Product.builder().category(category).productName(product.getProductName())
				.productPrice(price).mixed(false).build();
		return productRepository.save(newProduct);
	}

	@Override
	public Product updateProduct(Product product) {
		ProductCategory category = productCategoryRepository.findById(product.getCategory().getId())
				.orElseThrow(()-> new ItemNotFoundException("No se encontro la categoria"));
				

		return productRepository.findById(product.getId()).map(p -> {
			ProductPrice price = productPriceRepository.findById(p.getProductPrice().getId())
					.orElseThrow(()-> new ItemNotFoundException("No se encontro el precio"));
					
			price.setPrice(product.getProductPrice().getPrice());
			ProductPrice updatedPrice = productPriceRepository.save(price);
			p.setId(product.getId());
			p.setProductName(product.getProductName());
			p.setCategory(category);
			p.setProductPrice(updatedPrice);
			return productRepository.save(p);
		}).orElseThrow(() -> new ItemNotFoundException("No se encontro el producto"));

	}

	@Transactional
	@Override
	public Product createProductStock(Product product, ProductStock stock) {
		log.info("Recepcion de stock");
		if (stock.getProductStock() < 0) {
			log.info("Numero negativo");
			throw new NegativeNumberException("No se permite un numero negativo. ProductServiceImpl.saveProductStock");
		} else {
			ProductStock savedStock = productStockService.updateStock(stock, product.getId());
			product.setProductStock(savedStock);
			Product savedProduct = productRepository.save(product);
			if (savedProduct.isMixed()) {
				ingredientService.updateIngredientAmount(savedStock.getProductStock(), product.getId());
				return savedProduct;
			} else {
				return savedProduct;
			}
		}
	}

	@Override
	public void deleteProductById(Long id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException("No se encontro el producto. ProductServiceImpl.deleteProductById");
		}

	}

	@Override
	public List<Product> findAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public List<Product> findByCategoryId(Long id) {
		return (List<Product>) productRepository.findByCategoryId(id);
	}

}
