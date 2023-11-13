package com.lord.arbam.services_impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductPrice;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.repositories.ProductRepository;
import com.lord.arbam.repositories.RestoTableOrderRepository;
import com.lord.arbam.services.ProductPriceService;
import com.lord.arbam.services.ProductService;
import com.lord.arbam.services.RestoTableOrderService;
import com.lord.arbam.services.RestoTableService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestoTableOrderServiceImpl implements RestoTableOrderService {

	@Autowired
	private final RestoTableOrderRepository restoTableOrderRepository;

	@Autowired
	private final ProductService productService;

	@Autowired
	private final RestoTableService restoTableService;
	
	@Autowired
	private final ProductPriceService productPriceService;

	@Transactional
	@Override
	public RestoTableOrder findOrderById(Long id) {
		return restoTableOrderRepository.findById(id).orElseThrow(
				() -> new ItemNotFoundException("No se encontro la orden. RestoTableOrderServiceImpl.findOrderById"));
	}

	@Override
	public RestoTableOrder createOrder(RestoTableOrder order) {
		Optional<RestoTableOrder> existingOrder = restoTableOrderRepository
				.findByRestoTableIdAndProductId(order.getRestoTable().getId(), order.getProduct().getId());
		if(existingOrder.isPresent()) {
		return  updateOrder(existingOrder.get(), order.getProductQuantity());
		}
		Product product = productService.findProductById(order.getProduct().getId());
		RestoTable table = restoTableService.findRestoTableById(order.getRestoTable().getId());
		RestoTableOrder newOrder = RestoTableOrder.builder().product(product)
				.productQuantity(order.getProductQuantity()).restoTable(table)
				.totalOrderPrice(
						product.getProductPrice().getPrice().multiply(new BigDecimal(order.getProductQuantity())))
				.build();
		
		return restoTableOrderRepository.save(newOrder);
	}
	
	@Override
	public RestoTableOrder updateOrder(RestoTableOrder existingOrder,Integer productquantity) {
		ProductPrice price = productPriceService.findByProductId(existingOrder.getProduct().getId());
		existingOrder.setProductQuantity(existingOrder.getProductQuantity()+productquantity);
		existingOrder.setTotalOrderPrice(price.getPrice().multiply(new BigDecimal(existingOrder.getProductQuantity())));
		return restoTableOrderRepository.save(existingOrder);
				
	}

	@Override
	public List<RestoTableOrder> findAllOrders() {
		return (List<RestoTableOrder>) restoTableOrderRepository.findAll();
	}

	@Override
	public void deleteOderById(Long id) {
		if (restoTableOrderRepository.existsById(id)) {
			restoTableOrderRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException("No se encontro la orden, RestoTableOrderServiceImpl.deleteOderById");
		}

	}

	@Override
	public List<RestoTableOrder> findAllByRestoTableId(Long restoTableId) {
		return (List<RestoTableOrder>)restoTableOrderRepository.findAllByRestoTableId(restoTableId);
	}

	

	

}
