package com.lord.arbam.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lord.arbam.dto.IngredientItemStockReportDto;
import com.lord.arbam.dto.IngredientStockReportDto;
import com.lord.arbam.dto.IngredientStockUpdateReportDto;
import com.lord.arbam.dto.ProductStockDto;
import com.lord.arbam.dto.ProductStockUpdateReportDto;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.NegativeNumberException;
import com.lord.arbam.mapper.ProductMapper;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.repository.ProductCategoryRepository;
import com.lord.arbam.repository.ProductPriceRepository;
import com.lord.arbam.repository.ProductRepository;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.ProductPriceService;
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

	@Autowired
	private final ProductStockService productStockService;

	@Autowired
	private final IngredientService ingredientService;

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	private static final Sort sort = Sort.by("productName");

	@Override
	public Product findProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Product not found. ProductServiceImpl.findProductById"));
	}

	@Override
	public Product saveProduct(Product product) {
		log.info("Creating new porduct");
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
			log.info("Updating product");
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
			log.info("Deleting product");
			productRepository.deleteById(id);
		} else {
			log.info("Cant delete, product not found");
			throw new ItemNotFoundException("No se encontro el producto");
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

		return (List<Product>) productRepository.findAll(sort);
	}

	@Override
	public List<Long> findProductIdsByCategory(Long categoryId) {
		return (List<Long>) productRepository.findByCategoryId(categoryId).stream().map(p -> p.getId()).toList();
	}

	@Override
	public ProductStockUpdateReportDto increaseProductStock(long productId, ProductStockDto stockDto) {
		log.info("Buscando producto por id");
		Product product = findProductById(productId);
		int oldProductStock = 0;
		if (productStockService.checkStockPresent(productId)) {
			oldProductStock = productStockService.findStockByProductId(product.getId()).getProductStock();
		}

		if (product.isMixed()) {
			log.info("Actualizando la cantidad de ingredientes");
			List<IngredientStockUpdateReportDto> ingredientReportDtos = ingredientService
					.decreaseIngredientAmount(stockDto.getProductStock(), productId);
			ProductStock stock = ProductMapper.INSTANCE.toStock(stockDto);
			log.info("Creando o actualizando stock");
			ProductStock savedStock = productStockService.updateStock(stock, productId);
			log.info("Guardando producto");
			Product productUpdated = createProductStock(product, savedStock);
			ProductStockUpdateReportDto report = mapToProductUpdateReportDto(productUpdated, oldProductStock,
					savedStock, ingredientReportDtos);
			return report;
		}
		ProductStock stock = ProductMapper.INSTANCE.toStock(stockDto);
		log.info("Creando o actualizando stock");
		ProductStock savedStock = productStockService.updateStock(stock, productId);
		log.info("Guardando producto");
		Product productUpdated = createProductStock(product, savedStock);
		ProductStockUpdateReportDto report = mapToProductUpdateReportDto(productUpdated, oldProductStock, savedStock,
				new ArrayList<IngredientStockUpdateReportDto>());
		return report;

	}

	@Override
	public ProductStockUpdateReportDto decreaseProductStock(long productId, ProductStockDto stockDto) {
		Product product = findProductById(productId);
		int oldProductStock = 0;
		if (productStockService.checkStockPresent(productId)) {
			oldProductStock = productStockService.findStockByProductId(product.getId()).getProductStock();
		}
		oldProductStock = productStockService.findStockByProductId(product.getId()).getProductStock();
		ProductStock stock = ProductMapper.INSTANCE.toStock(stockDto);
		log.info("Eliminando stock");
		ProductStock savedStock = productStockService.reduceStock(stock, productId);
		if (product.isMixed()) {
			log.info("Actualizando la cantidad de ingredientes");
			List<IngredientStockUpdateReportDto> ingredientReportDtos = ingredientService
					.increaseIngredientAmount(stockDto.getProductStock(), productId);
			ProductStockUpdateReportDto report = mapToProductUpdateReportDto(product, oldProductStock, savedStock,
					ingredientReportDtos);
			return report;

		}
		ProductStockUpdateReportDto report = mapToProductUpdateReportDto(product, oldProductStock, savedStock,
				new ArrayList<IngredientStockUpdateReportDto>());
		return report;
	}

	private static ProductStockUpdateReportDto mapToProductUpdateReportDto(Product product, int oldProductStock,
			ProductStock savedStock, List<IngredientStockUpdateReportDto> ingredientReportDtos) {
		ProductStockUpdateReportDto productStockUpdateReportDto = new ProductStockUpdateReportDto();
		productStockUpdateReportDto.setProductName(product.getProductName());
		productStockUpdateReportDto.setProductOldQuantity(oldProductStock);
		productStockUpdateReportDto.setProductNewQuantity(savedStock.getProductStock());
		productStockUpdateReportDto.setIngrdientStockReports(ingredientReportDtos);
		return productStockUpdateReportDto;
	}

	@Override
	public IngredientStockReportDto checkIngredientsStock(long productId, int stock) {
		log.info("Checking ingredients stock");
		Product product = findProductById(productId);
		IngredientStockReportDto ingredientStockReportDto = new IngredientStockReportDto();
		ingredientStockReportDto.setProductName(product.getProductName());
		ingredientStockReportDto.setStockRequestedQuantity(stock);
		List<IngredientItemStockReportDto> ingredientReports = ingredientService.checkIngredientsStock(product.getId(), stock);
		ingredientStockReportDto.setIngredients(ingredientReports);
		return ingredientStockReportDto;
	}

}
