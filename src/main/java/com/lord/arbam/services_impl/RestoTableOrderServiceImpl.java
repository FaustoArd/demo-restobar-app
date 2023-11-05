package com.lord.arbam.services_impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.repositories.ProductRepository;
import com.lord.arbam.repositories.RestoTableOrderRepository;
import com.lord.arbam.services.RestoTableOrderService;

@Service
public class RestoTableOrderServiceImpl implements RestoTableOrderService{
	
	@Autowired
	private final RestoTableOrderRepository restoTableOrderRepository;
	
	@Autowired
	private final ProductRepository productRepository;
	
	public RestoTableOrderServiceImpl(RestoTableOrderRepository restoTableOrderRepository,ProductRepository productRepository) {
		this.restoTableOrderRepository = restoTableOrderRepository;
		this.productRepository = productRepository;
	}

	@Override
	public RestoTableOrder findOrderById(Long id) {
	return  restoTableOrderRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No se encontro la orden. RestoTableOrderServiceImpl.findOrderById"));
	}

	@Override
	public RestoTableOrder createOrder(RestoTableOrder order) {
		Product product = productRepository.findById(order.getProduct().getId())
				.orElseThrow(()-> new ItemNotFoundException("No se encontro el producto. RestoTableOrderServiceImpl.saveOrder"));
		RestoTableOrder newOrder = RestoTableOrder.builder()
				.product(product)
				.productQuantity(order.getProductQuantity())
				.totalOrderPrice(product.getProductPrice().getPrice() * order.getProductQuantity()).build();
		
		return restoTableOrderRepository.save(newOrder);
	}

	@Override
	public List<RestoTableOrder> findAllOrders() {
	return (List<RestoTableOrder>)restoTableOrderRepository.findAll();
	}

	@Override
	public void deleteOderById(Long id) {
		if(restoTableOrderRepository.existsById(id)) {
			restoTableOrderRepository.deleteById(id);
		}else {
			throw new ItemNotFoundException("No se encontro la orden, RestoTableOrderServiceImpl.deleteOderById");
		}
		
	}

	@Override
	public List<RestoTableOrder> findByRestoTablesId(Long id) {
		return restoTableOrderRepository.findByRestoTablesId(id)
				.orElseThrow(()-> new ItemNotFoundException("No se encontraron las ordenes, RestoTableOrderServiceImpl.deleteOderById"));
	}

}
