package com.lord.arbam.services_impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.exceptions.NegativeNumberException;
import com.lord.arbam.exceptions.ProductOutOfStockException;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.repositories.ProductStockRepository;
import com.lord.arbam.services.ProductStockService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {
	
	@Autowired
	private final ProductStockRepository productStockRepository;

	

	@Override
	public ProductStock saveStock(ProductStock productStock) {
	return productStockRepository.save(productStock);
	}

	@Override
	public ProductStock findStockById(Long id) {
		return productStockRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("No se encontro el stock. ProductStockServiceImpl.findById"));
	}

	@Override
	public ProductStock updateStock(ProductStock stock, Long  productId) {
		
		Optional<ProductStock> stockResult = productStockRepository.findStockByProductId(productId);
		if(stockResult.isPresent()) {
			ProductStock updatedStock = stockResult.get();
			updatedStock.setProductStock(stock.getProductStock());
			return productStockRepository.save(updatedStock);
		}else {
			ProductStock newStock = new ProductStock(stock.getProductStock());
			return productStockRepository.save(newStock);
		}
	}

	@Override
	public ProductStock findStockByProductId(Long id) {
		return productStockRepository.findStockByProductId(id)
				.orElseThrow(()-> new ItemNotFoundException("No se encontro el stock. ProductStockServiceImpl.findStockByProductId"));
	}

	@Transactional
	@Override
	public void subTractStock(Integer amount,Long productId) {
		if(amount<1) {
			throw new NegativeNumberException("No se permite cantidad 0 , o una cantidad negativa");
		}
		ProductStock productStock = findStockByProductId(productId);
		if(productStock.getProductStock()<amount) {
			throw new ProductOutOfStockException("No hay suficiente stock");
		}else {
			productStock.setProductStock(productStock.getProductStock()-amount);
		}
		productStockRepository.save(productStock);
		
	}
	
}
