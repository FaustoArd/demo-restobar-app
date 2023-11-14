package com.lord.arbam.services_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.ProductPrice;
import com.lord.arbam.repositories.ProductPriceRepository;
import com.lord.arbam.services.ProductPriceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

	
	@Autowired
	private final ProductPriceRepository productPriceRepository;
	


	@Override
	public ProductPrice savePrice(ProductPrice price) {
	return productPriceRepository.save(price);
	}

	@Override
	public ProductPrice findPriceById(Long id) {
		return productPriceRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No se encontro el precio. ProductPriceServiceImpl.findById"));
	}

	@Override
	public ProductPrice updatePrice(ProductPrice price) {
		ProductPrice updatedPrice = productPriceRepository.findById(price.getId())
				.orElseThrow(()-> new ItemNotFoundException("No se encontro el precio. ProductPriceServiceImpl.findById"));
		updatedPrice.setPrice(price.getPrice());
		return productPriceRepository.save(price);
	}

	@Override
	public ProductPrice findByProductId(Long id) {
		return productPriceRepository.findByProductId(id).orElseThrow(()-> new ItemNotFoundException("No se encontro el precio"));
	}
}

