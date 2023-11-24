package com.lord.arbam.service_impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.NegativeNumberException;
import com.lord.arbam.exception.ProductOutOfStockException;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.repository.ProductStockRepository;
import com.lord.arbam.service.ProductStockService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {

	@Autowired
	private final ProductStockRepository productStockRepository;

	private Logger log = LoggerFactory.getLogger(ProductStock.class);

	@Override
	public ProductStock saveStock(ProductStock productStock) {
		return productStockRepository.save(productStock);
	}

	@Override
	public ProductStock findStockById(Long id) {
		return productStockRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el stock"));
	}
	
	@Transactional
	@Override
	public ProductStock updateStock(ProductStock stock, Long productId) {
		log.info("Recepcion de stock");
		if (stock.getProductStock() < 0) {
			log.warn("Stock con numero negativo");
			throw new NegativeNumberException("No se permite un numero negativo. ProductServiceImpl.saveProductStock");
		} else {
			log.info("Checkeando si es nuevo stock o update");
			Optional<ProductStock> stockResult = productStockRepository.findStockByProductId(productId);
			if (stockResult.isPresent()) {
				log.info("Existente , actualizando stock");
				ProductStock updatedStock = stockResult.get();
				updatedStock.setProductStock(updatedStock.getProductStock()+ stock.getProductStock());
				return productStockRepository.save(updatedStock);
			} else {
				log.info("Creando Nuevo stock");
				ProductStock newStock = new ProductStock(stock.getProductStock());
				return productStockRepository.save(newStock);
			}
		}
	}
	
	@Transactional
	@Override
	public ProductStock reduceStock(ProductStock stockReduced, Long productId) {
		log.info("Eliminacion de stock");
		if(stockReduced.getProductStock()<0) {
			log.warn("Stock con numero negativo");
			throw new NegativeNumberException("Negative number is not allowed.");
		}else {
			Optional<ProductStock> stockResult = productStockRepository.findStockByProductId(productId);
			if(stockResult.isEmpty()) {
				log.info("Stock inexistente");
				throw new ItemNotFoundException("Stock not found");
			}else {
				log.info("Eliminando stock");
				ProductStock deletedStock = stockResult.get();
				deletedStock.setProductStock(deletedStock.getProductStock() - stockReduced.getProductStock());
				return productStockRepository.save(deletedStock);
			}
		}
		
	}
	
	
	

	@Override
	public ProductStock findStockByProductId(Long id) {
		return productStockRepository.findStockByProductId(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el stock"));
	}
	


	@Transactional
	@Override
	public void subTractStockFromOrder(Integer amount, Long productId) {
		if (amount < 1) {
			throw new NegativeNumberException("No se permite cantidad 0 , o una cantidad negativa");
		}
		ProductStock productStock = findStockByProductId(productId);
		if (productStock.getProductStock() < amount) {
			throw new ProductOutOfStockException("No hay suficiente stock");
		} else {
			productStock.setProductStock(productStock.getProductStock() - amount);
		}
		productStockRepository.save(productStock);

	}

	
	

}
