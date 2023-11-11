package com.lord.arbam.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.RestoTableOrderDto;
import com.lord.arbam.mappers.RestoTableOrderMapper;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.services.RestoTableOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/orders")
@RequiredArgsConstructor
public class RestoTableOrderController {
	
	private static final Logger log = LoggerFactory.getLogger(RestoTableOrderController.class);
	
	@Autowired
	private final RestoTableOrderService restoTableOrderService;
	
	@PostMapping("/create_order")
	ResponseEntity<RestoTableOrderDto> createOrder(@RequestBody RestoTableOrderDto restoTableOrderDto){
		log.info("Create new Order");
		RestoTableOrder order = RestoTableOrderMapper.INSTANCE.toOrder(restoTableOrderDto);
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		RestoTableOrderDto  createdOrderDto = RestoTableOrderMapper.INSTANCE.toOrderDto(createdOrder);
		return new ResponseEntity<RestoTableOrderDto>(createdOrderDto,HttpStatus.CREATED);
	}

}
