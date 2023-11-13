package com.lord.arbam.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/resto_tables")
@RequiredArgsConstructor
public class RestoTableController {
	
	private static final Logger log = LoggerFactory.getLogger(RestoTableController.class);
	
	@Autowired
	private final RestoTableService restoTableService;
	
	@Autowired
	private final RestoTableOrderService restoTableOrderService;
	
	

	@GetMapping("/{id}")
	ResponseEntity<RestoTableDto> findRestoTableById(@PathVariable("id")Long id){
		RestoTable table = restoTableService.findRestoTableById(id);
		RestoTableDto tableDto = RestoTableMapper.INSTANCE.toRestotableDto(table);
		return new ResponseEntity<RestoTableDto>(tableDto,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	ResponseEntity<List<RestoTableDto>> findAll(){
		List<RestoTable> tables = restoTableService.findAllRestoTables();
		List<RestoTableDto> tablesDto = RestoTableMapper.INSTANCE.toRestoTablesDto(tables);
		return new ResponseEntity<List<RestoTableDto>>(tablesDto,HttpStatus.OK);
	}
	@GetMapping("/all_id_asc")
	ResponseEntity<List<RestoTableDto>> findAllOrderByIdAsc(){
		List<RestoTable> tables = restoTableService.findAllByOrderByIdAsc();
		List<RestoTableDto> tablesDto = RestoTableMapper.INSTANCE.toRestoTablesDto(tables);
		return new ResponseEntity<List<RestoTableDto>>(tablesDto,HttpStatus.OK);
	}
	
	@PostMapping("/open_table")
	ResponseEntity<RestoTableDto> openRestoTable(@RequestBody RestoTableDto restoTableDto){
		log.info("Create new Table");
		RestoTable table = RestoTableMapper.INSTANCE.toRestoTable(restoTableDto);
		RestoTable createdTable = restoTableService.openRestoTable(table);
		RestoTableDto createdTableDto = RestoTableMapper.INSTANCE.toRestotableDto(createdTable);
		return new ResponseEntity<RestoTableDto>(createdTableDto,HttpStatus.CREATED);
	}
	@PutMapping("/update_price/{id}")
	ResponseEntity<RestoTableDto> udpateRestoTablePrice(@PathVariable("id")Long id){
		RestoTable table = restoTableService.findRestoTableById(id);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(id);
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		RestoTableDto updatedTableDto = RestoTableMapper.INSTANCE.toRestotableDto(updatedTable);
		return new ResponseEntity<RestoTableDto>(updatedTableDto,HttpStatus.OK);
	}
	

	@DeleteMapping("/delete_order")
	ResponseEntity<String> deleteOrder(@PathVariable("id") Long id){
		log.info("Delete order from table");
		restoTableOrderService.deleteOderById(id);
		return new ResponseEntity<String>("La orden se elimino con exito", HttpStatus.OK);
	}
	@GetMapping("/all_orders_by_restotable_id/{id}")
	ResponseEntity<List<RestoTableOrderDto>> findOrdersByRestoTableId(@PathVariable("id")Long id){
		return null;
	}
	
	
}
