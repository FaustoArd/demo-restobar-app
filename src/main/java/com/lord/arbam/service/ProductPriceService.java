package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.dto.PriceUpdateReportDto;
import com.lord.arbam.model.ProductPrice;

public interface ProductPriceService {
	
	public ProductPrice savePrice(ProductPrice price);
	
	public ProductPrice findPriceById(Long id);
	
	//public ProductPrice updatePrice(ProductPrice price);
	
	public ProductPrice findByProductId(Long id);
	
	public List<PriceUpdateReportDto> updateAllPriceByPercentageByCategory(List<Long> productIds,double percentage,boolean positive);

}
