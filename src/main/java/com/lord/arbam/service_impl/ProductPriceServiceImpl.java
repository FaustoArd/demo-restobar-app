package com.lord.arbam.service_impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.repository.ProductPriceRepository;
import com.lord.arbam.service.ProductPriceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

	
	@Autowired
	private final ProductPriceRepository productPriceRepository;
	

	private static final Logger log = LoggerFactory.getLogger(ProductPriceService.class);

	@Override
	public ProductPrice savePrice(ProductPrice price) {
		log.info("Saving product price");
	return productPriceRepository.save(price);
	}

	@Override
	public ProductPrice findPriceById(Long id) {
		log.info("Find price by id");
		return productPriceRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No se encontro el precio. ProductPriceServiceImpl.findById"));
	}

	@Override
	public ProductPrice updatePrice(ProductPrice price) {
		log.info("Update proruce price");
		ProductPrice updatedPrice = productPriceRepository.findById(price.getId())
				.orElseThrow(()-> new ItemNotFoundException("No se encontro el precio. ProductPriceServiceImpl.findById"));
		updatedPrice.setPrice(price.getPrice());
		return productPriceRepository.save(price);
	}

	@Override
	public ProductPrice findByProductId(Long id) {
		log.info("Find price by product id");
		return productPriceRepository.findByProductId(id).orElseThrow(()-> new ItemNotFoundException("No se encontro el precio"));
	}

	@Override
	public void updateAllPriceByPercentage(double percentage,boolean positive) {
		log.info("Update price by percentage");
		List<ProductPrice> updatedPrices =  productPriceRepository.findAll().stream().map(itemPrice -> {
			BigDecimal updatedPrice = itemPrice.getPrice().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100));;
			if(positive) {
				itemPrice.setPrice(itemPrice.getPrice().add(updatedPrice));
				return itemPrice;
			}else {
				itemPrice.setPrice(itemPrice.getPrice().subtract(updatedPrice));
				return itemPrice;
			}
		}).toList();
		
		productPriceRepository.saveAll(updatedPrices);
	}
}

