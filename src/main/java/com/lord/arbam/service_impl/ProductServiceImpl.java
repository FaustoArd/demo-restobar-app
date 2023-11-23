package com.lord.arbam.service_impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.NegativeNumberException;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.repository.ProductCategoryRepository;
import com.lord.arbam.repository.ProductPriceRepository;
import com.lord.arbam.repository.ProductRepository;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.ProductService;
import com.lord.arbam.service.ProductStockService;

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

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public Product findProductById(Long id) {
		return productRepository.findById(id).orElseThrow(
				() -> new ItemNotFoundException("Product not found. ProductServiceImpl.findProductById"));
	}

	@Override
	public Product saveProduct(Product product) {
		log.info("Creando nuevo producto");
		ProductCategory category = productCategoryRepository.findById(product.getCategory().getId())
				.orElseThrow(() -> new ItemNotFoundException("Category not found"));

		ProductPrice price = productPriceRepository.save(new ProductPrice(product.getProductPrice().getPrice()));

		Product newProduct = Product.builder().category(category).productName(product.getProductName())
				.productPrice(price).mixed(false).build();
		return productRepository.save(newProduct);
	}

	@Override
	public Product updateProduct(Product product) {
		ProductCategory category = productCategoryRepository.findById(product.getCategory().getId())
				.orElseThrow(() -> new ItemNotFoundException("Category not found"));

		return productRepository.findById(product.getId()).map(p -> {
			ProductPrice price = productPriceRepository.findById(p.getProductPrice().getId())
					.orElseThrow(() -> new ItemNotFoundException("Price not found"));
			log.info("Actualizando el producto");
			price.setPrice(product.getProductPrice().getPrice());
			ProductPrice updatedPrice = productPriceRepository.save(price);
			p.setId(product.getId());
			p.setProductName(product.getProductName());
			p.setCategory(category);
			p.setProductPrice(updatedPrice);
			return productRepository.save(p);
		}).orElseThrow(() -> new ItemNotFoundException("Product not found"));

	}

	@Transactional
	@Override
	public Product createProductStock(Product product, ProductStock stock) {
		log.info("Recepcion de stock en tabla producto");
		if (stock.getProductStock() < 0) {
			log.info("Stock con numero negativo");
			throw new NegativeNumberException("Negative number is not allowed. ProductServiceImpl.createProductStock");
		} else {
			log.info("Guardando stock en tabla producto");
			product.setProductStock(stock);
			return productRepository.save(product);
		}
	}

	@Override
	public void deleteProductById(Long id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException("Product not found. ProductServiceImpl.deleteProductById");
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

	@Override
	public List<Product> findAllProductByProductNameOrderAsc() {
		Sort sort =  Sort.by("productName");
		return (List<Product>)productRepository.findAll(sort);
	}
	


}
