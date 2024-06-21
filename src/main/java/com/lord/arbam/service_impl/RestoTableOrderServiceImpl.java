package com.lord.arbam.service_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.dto.ProductDto;
import com.lord.arbam.dto.RestoTableOrderDto;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableOrder;
import com.lord.arbam.repository.ProductPriceRepository;
import com.lord.arbam.repository.ProductRepository;
import com.lord.arbam.repository.ProductStockRepository;
import com.lord.arbam.repository.RestoTableOrderRepository;
import com.lord.arbam.repository.RestoTableRepository;
import com.lord.arbam.service.RestoTableOrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestoTableOrderServiceImpl implements RestoTableOrderService {

	private static final Logger log = LoggerFactory.getLogger(RestoTableOrderServiceImpl.class);

	@Autowired
	private final RestoTableOrderRepository restoTableOrderRepository;

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final RestoTableRepository restoTableRepository;

	@Autowired
	private final ProductPriceRepository productPriceRepository;

	@Autowired
	private final ProductStockRepository productStockRepository;

	@Transactional
	@Override
	public RestoTableOrder findOrderById(Long id) {
		return restoTableOrderRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Price not found"));
	}

	@Override
	public RestoTableOrder createOrder(RestoTableOrder order) {
		if(!order.isAmount()) {
		Optional<RestoTableOrder> existingOrder = restoTableOrderRepository
				.findByRestoTableIdAndProductId(order.getRestoTable().getId(), order.getProduct().getId());
		if (existingOrder.isPresent()) {
			log.info("orden existente,actualizando...");
			return updateOrder(existingOrder.get(), order.getProductQuantity());
		}
		}
		RestoTable table = findRestoTableById(order.getRestoTable().getId());
		
		if(order.isAmount()){
			RestoTableOrder newAmount = RestoTableOrder.builder()
					.productQuantity(order.getProductQuantity())
					.restoTable(table)
					.amount(order.isAmount())
					.totalOrderPrice(order.getTotalOrderPrice()).build();
			RestoTableOrder savedAmount =  restoTableOrderRepository.save(newAmount);
			//savedAmount.setProduct(Product.builder().productName("Monto parcial").build());
			return savedAmount;
			
		}
		Product product = findProductById(order.getProduct().getId());
		RestoTableOrder newOrder = RestoTableOrder.builder().product(product)
				.productQuantity(order.getProductQuantity()).restoTable(table)
				.totalOrderPrice(
						product.getProductPrice().getPrice().multiply(new BigDecimal(order.getProductQuantity())))
				.build();
		log.info("Nueva orden creada");

		return restoTableOrderRepository.save(newOrder);
	}
	
		@Override
	public RestoTableOrder updateOrder(RestoTableOrder existingOrder, Integer productQuantity) {
		ProductPrice price = productPriceRepository.findByProductId(existingOrder.getProduct().getId())
				.orElseThrow(() -> new ItemNotFoundException("Price not found"));
		existingOrder.setProductQuantity(existingOrder.getProductQuantity() + productQuantity);
		existingOrder.setTotalOrderPrice(price.getPrice().multiply(new BigDecimal(existingOrder.getProductQuantity())));
		log.info("orden actualizada.");
		return restoTableOrderRepository.save(existingOrder);

	}

	@Override
	public List<RestoTableOrder> findAllOrders() {
		log.info("buscando todas las ordenes");
		return (List<RestoTableOrder>) restoTableOrderRepository.findAll();
	}

	@Override
	public void deleteOderById(Long id) {
		if (restoTableOrderRepository.existsById(id)) {
			RestoTableOrder orderItemToBeDeleted = findOrderById(id);
			ProductStock stock = findProductStockById(orderItemToBeDeleted.getProduct().getProductStock().getId());
			if (orderItemToBeDeleted.getProductQuantity() == 1) {
				log.info("La orden es 1 , eliminar orden, id:" + id + ", Stock agregado: 1");
				restoTableOrderRepository.deleteById(id);
				stock.setProductStock(stock.getProductStock() + 1);
				productStockRepository.save(stock);

			} else {
				stock.setProductStock(stock.getProductStock() + 1);
				productStockRepository.save(stock);
				updateDeletedOrderItemPrice(orderItemToBeDeleted);
				log.info("Se elimino la cantidad 1, de la orden  id:" + id + ", Stock agregado: 1");

			}

		} else {
			log.error("La orden a eliminar no existe.");
			throw new ItemNotFoundException("Order not found");
		}

	}

	@Override
	public RestoTableOrder updateDeletedOrderItemPrice(RestoTableOrder orderItemToBeDeleted) {
		log.error("Eliminando item, cantidad: 1");
		ProductPrice price = productPriceRepository.findByProductId(orderItemToBeDeleted.getProduct().getId())
				.orElseThrow(() -> new ItemNotFoundException("Price not found"));
		orderItemToBeDeleted.setProductQuantity(orderItemToBeDeleted.getProductQuantity() - 1);
		orderItemToBeDeleted.setTotalOrderPrice(
				price.getPrice().multiply(new BigDecimal(orderItemToBeDeleted.getProductQuantity())));
		log.info("guardando orden actualizada.");
		return restoTableOrderRepository.save(orderItemToBeDeleted);
	}

	@Override
	public List<RestoTableOrder> findAllByRestoTableId(Long restoTableId) {
		return (List<RestoTableOrder>) restoTableOrderRepository.findAllByRestoTableId(restoTableId);
	}

	private ProductStock findProductStockById(long productStockId) {
		return productStockRepository.findById(productStockId)
				.orElseThrow(() -> new ItemNotFoundException("Stock not found"));
	}

	private Product findProductById(long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new ItemNotFoundException("Product not found"));
	}

	private RestoTable findRestoTableById(long restoTableId) {
		return restoTableRepository.findById(restoTableId)
				.orElseThrow(() -> new ItemNotFoundException("Resto table not found"));
	}

	@Override
	public List<RestoTableOrderDto> addAmountOrderName(List<RestoTableOrderDto> restoTableOrderDtos) {
		return restoTableOrderDtos.stream().map(dto -> {
			if(dto.isAmount()) {
				dto.setProductName("Monto parcial");
				return dto;
			}
			return dto;
		}).toList();
		
	}

	

}
