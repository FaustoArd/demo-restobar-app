package com.lord.arbam.services_impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.repositories.ProductStockRepository;
import com.lord.arbam.services.ProductStockService;

@Service
public class ProductStockServiceImpl implements ProductStockService {
	
	@Autowired
	private final ProductStockRepository productStockRepository;

	public ProductStockServiceImpl( ProductStockRepository productStockRepository) {
		this.productStockRepository = productStockRepository;
	}

	@Override
	public ProductStock saveProductStock(ProductStock productStock) {
	return productStockRepository.save(productStock);
	}

	@Override
	public ProductStock findProductStockById(Long id) {
		return productStockRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("No se encontro el stock. ProductStockServiceImpl.findById"));
	}

	@Override
	public ProductStock updateProductStock(ProductStock stock, Long  productId) {
		
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
	
}
