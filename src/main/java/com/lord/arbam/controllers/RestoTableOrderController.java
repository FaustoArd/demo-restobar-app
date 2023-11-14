package com.lord.arbam.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.RestoTableOrderDto;
import com.lord.arbam.mappers.RestoTableOrderMapper;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.services.ProductStockService;
import com.lord.arbam.services.RestoTableOrderService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/orders")
@RequiredArgsConstructor
public class RestoTableOrderController {
	
	
	
	@Autowired
	private final RestoTableOrderService restoTableOrderService;
	
	private final Gson gson = new Gson();
	
	@Autowired
	private final ProductStockService productStockService;
	
	@PostMapping("/create_order")
	ResponseEntity<RestoTableOrderDto> createOrder(@RequestBody RestoTableOrderDto restoTableOrderDto){
		
		productStockService.subTractStock(restoTableOrderDto.getProductQuantity(), restoTableOrderDto.getProductId());
		RestoTableOrder order = RestoTableOrderMapper.INSTANCE.toOrder(restoTableOrderDto);
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		RestoTableOrderDto  createdOrderDto = RestoTableOrderMapper.INSTANCE.toOrderDto(createdOrder);
		return new ResponseEntity<RestoTableOrderDto>(createdOrderDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/all_by_restotable/{restoTableId}")
	ResponseEntity<List<RestoTableOrderDto>> findAllOrdersByRestoTable(@PathVariable("restoTableId")Long restoTableId){
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(restoTableId);
		List<RestoTableOrderDto> ordersDto = RestoTableOrderMapper.INSTANCE.toOrdersDto(orders);
		return new ResponseEntity<List<RestoTableOrderDto>>(ordersDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<String> deleteOrderById(@PathVariable("id")Long id){
		
		restoTableOrderService.deleteOderById(id);
		return new ResponseEntity<String>(gson.toJson("La orden fue eliminada con exito"),HttpStatus.OK);
	}

}
