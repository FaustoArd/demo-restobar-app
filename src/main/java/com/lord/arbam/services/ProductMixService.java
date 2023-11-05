package com.lord.arbam.services;



import java.util.List;

import com.lord.arbam.models.ProductMix;

public interface ProductMixService {
	
	public ProductMix findProductMixById(Long id);
	
	public ProductMix saveProductMix(ProductMix productMix);
	
	public ProductMix updateProducMix(ProductMix productMix);
	
	public List<ProductMix> findByProductId(Long id);
	
	public void deleteProductMixById(Long id);
	
	public List<ProductMix> findAllProductsMixes();

}