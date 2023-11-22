package com.lord.arbam.services_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductPrice;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.repositories.ProductPriceRepository;
import com.lord.arbam.repositories.ProductRepository;
import com.lord.arbam.repositories.ProductStockRepository;
import com.lord.arbam.repositories.RestoTableOrderRepository;
import com.lord.arbam.repositories.RestoTableRepository;
import com.lord.arbam.services.RestoTableOrderService;
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
		return restoTableOrderRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro la orden"));
	}

	@Override
	public RestoTableOrder createOrder(RestoTableOrder order) {
		Optional<RestoTableOrder> existingOrder = restoTableOrderRepository
				.findByRestoTableIdAndProductId(order.getRestoTable().getId(), order.getProduct().getId());
		if (existingOrder.isPresent()) {
			log.info("orden existente,actualizando...");
			return updateOrder(existingOrder.get(), order.getProductQuantity());
		}
		Product product = productRepository.findById(order.getProduct().getId()).orElseThrow(()-> new ItemNotFoundException("No se encontro el producto"));
		RestoTable table = restoTableRepository.findById(order.getRestoTable().getId()).orElseThrow(()-> new ItemNotFoundException("No se encontro la mesa"));
		RestoTableOrder newOrder = RestoTableOrder.builder().product(product)
				.productQuantity(order.getProductQuantity()).restoTable(table)
				.totalOrderPrice(
						product.getProductPrice().getPrice().multiply(new BigDecimal(order.getProductQuantity())))
				.build();
		log.info("Nueva orden creada");

		return restoTableOrderRepository.save(newOrder);
	}

	@Override
	public RestoTableOrder updateOrder(RestoTableOrder existingOrder, Integer productquantity) {
		ProductPrice price = productPriceRepository.findByProductId(existingOrder.getProduct().getId())
				.orElseThrow(()-> new ItemNotFoundException("No se encontro el precio"));
		existingOrder.setProductQuantity(existingOrder.getProductQuantity() + productquantity);
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
			RestoTableOrder orderToBeDeleted = findOrderById(id);
			ProductStock stock = productStockRepository
					.findById(orderToBeDeleted.getProduct().getProductStock().getId())
					.orElseThrow(() -> new ItemNotFoundException("No se encontro el stock"));
			stock.setProductStock(stock.getProductStock() + orderToBeDeleted.getProductQuantity());
			productStockRepository.save(stock);
			log.info("Orden eliminada. id:" + id + ", Stock agregado: " + orderToBeDeleted.getProductQuantity());
			restoTableOrderRepository.deleteById(id);
		} else {
			log.error("La orden a eliminar no existe.");
			throw new ItemNotFoundException("No se encontro la orden");
		}

	}

	@Override
	public List<RestoTableOrder> findAllByRestoTableId(Long restoTableId) {
		return (List<RestoTableOrder>) restoTableOrderRepository.findAllByRestoTableId(restoTableId);
	}

}
