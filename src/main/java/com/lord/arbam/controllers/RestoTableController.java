package com.lord.arbam.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.RestoTableDto;
import com.lord.arbam.dtos.RestoTableOrderDto;
import com.lord.arbam.mappers.RestoTableMapper;
import com.lord.arbam.mappers.RestoTableOrderMapper;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.services.RestoTableOrderService;
import com.lord.arbam.services.RestoTableService;

@RestController
@RequestMapping("/api/v1/arbam/resto_tables")
public class RestoTableController {
	
	private static final Logger log = LoggerFactory.getLogger(RestoTableController.class);
	
	@Autowired
	private final RestoTableService restoTableService;
	
	@Autowired
	private final RestoTableOrderService restoTableOrderService;
	
	

	public RestoTableController(RestoTableOrderService restoTableOrderService,RestoTableService restoTableService) {
		this.restoTableOrderService = restoTableOrderService;
		this.restoTableService = restoTableService;
	}
	
	@PostMapping("/new_table")
	ResponseEntity<RestoTableDto> createNewTable(@RequestBody RestoTableDto restoTableDto){
		log.info("Create new Table");
		RestoTable table = RestoTableMapper.INSTANCE.toRestoTable(restoTableDto);
		RestoTable createdTable = restoTableService.createRestoTable(table);
		RestoTableDto createdTableDto = RestoTableMapper.INSTANCE.toRestotableDto(createdTable);
		return new ResponseEntity<RestoTableDto>(createdTableDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/create_order")
	ResponseEntity<RestoTableOrderDto> createOrder(@RequestBody RestoTableOrderDto restoTableOrderDto){
		log.info("Create new Order");
		RestoTableOrder order = RestoTableOrderMapper.INSTANCE.toOrder(restoTableOrderDto);
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		RestoTableOrderDto  createdOrderDto = RestoTableOrderMapper.INSTANCE.toOrderDto(createdOrder);
		return new ResponseEntity<RestoTableOrderDto>(createdOrderDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/add_order")
	ResponseEntity<RestoTableDto> addOrderToRestoTable(@RequestBody RestoTableDto restoTableDto){
		log.info("Add order to RestoTable");
		RestoTable table = RestoTableMapper.INSTANCE.toRestoTable(restoTableDto);
		RestoTable updatedTable = restoTableService.addOrderToRestotable(table);
		RestoTable updatedTablePrice = restoTableService.updateRestoTablePrice(updatedTable);
		RestoTableDto updatedTablePriceDto = RestoTableMapper.INSTANCE.toRestotableDto(updatedTablePrice);
		return new ResponseEntity<RestoTableDto>(updatedTablePriceDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/update_table_price")
	ResponseEntity<RestoTableDto> updateRestoTablePrice(@RequestBody RestoTableDto restoTableDto){
		RestoTable table = RestoTableMapper.INSTANCE.toRestoTable(restoTableDto);
		RestoTable udpatedTable = restoTableService.updateRestoTablePrice(table);
		RestoTableDto updatedTableDto = RestoTableMapper.INSTANCE.toRestotableDto(udpatedTable);
		return new ResponseEntity<RestoTableDto>(updatedTableDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete_order")
	ResponseEntity<String> deleteOrder(@PathVariable("id") Long id){
		log.info("Delete order from table");
		restoTableOrderService.deleteOderById(id);
		return new ResponseEntity<String>("La orden se elimino con exito", HttpStatus.OK);
	}
	
	
}
