package com.lord.arbam.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductMix;
import com.lord.arbam.repository.ProductMixRepository;
import com.lord.arbam.repository.ProductRepository;
import com.lord.arbam.service.ProductMixService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMixServiceImpl implements ProductMixService {

	@Autowired
	private final ProductMixRepository productMixRepository;
	
	private final ProductRepository productRepository;

	

	@Override
	public ProductMix findProductMixById(Long id) {
		return productMixRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro la mezcla de ingredientes"));
	}

	@Transactional
	@Override
	public ProductMix saveProductMix(ProductMix productMix) {
		Product product = productRepository.findById(productMix.getProduct().getId()).orElseThrow(() -> new ItemNotFoundException("no se encontro el producto"));
		product.setMixed(true);
		productRepository.save(product);
		return productMixRepository.save(productMix);
	}

	@Override
	public ProductMix updateProducMix(ProductMix productMix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductMixById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProductMix> findAllProductsMixes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductMix> findByProductId(Long id) {
		return (List<ProductMix>) productMixRepository.findByProductId(id).orElseThrow(() -> new ItemNotFoundException(
				"No se encontro la receta."));
	}

}
