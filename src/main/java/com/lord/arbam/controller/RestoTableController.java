package com.lord.arbam.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dto.PaymentMethodDto;
import com.lord.arbam.dto.RestoTableDto;
import com.lord.arbam.mapper.RestoTableMapper;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableOrder;
import com.lord.arbam.service.RestoTableOrderService;
import com.lord.arbam.service.RestoTableService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/resto_tables")
@RequiredArgsConstructor
public class RestoTableController {
	
	private  final Gson gson = new Gson();
	
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
		
		RestoTable table = RestoTableMapper.INSTANCE.toRestoTable(restoTableDto);
		System.err.println(table.getId());
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
	

	
	@PutMapping("/close_table")
	ResponseEntity<String> closeTable(@RequestParam("restoTableId")
	Long restoTableId,@RequestParam("workingDayId")Long workingDayId,@RequestBody PaymentMethod paymentMethod){
		
		restoTableService.closeRestoTable(restoTableId,workingDayId,paymentMethod);
		return new ResponseEntity<String>(gson.toJson("La mesa fue cerrada con exito"),HttpStatus.OK);
	}
	
	@GetMapping("/all_methods")
	ResponseEntity<List<PaymentMethodDto>> findAllPaymentsMethods(){
		List<PaymentMethod> methods = restoTableService.findAllPaymentMethods();
		List<PaymentMethodDto> methodsDto = RestoTableMapper.INSTANCE.toPaymentMethodsDto(methods);
		return new ResponseEntity<List<PaymentMethodDto>>(methodsDto,HttpStatus.OK);
	}
	
	@GetMapping("/all_orders_by_restotable_id/{id}")
	ResponseEntity<String> findOrdersByRestoTableId(@PathVariable("id")Long id){
		return new ResponseEntity<String>(gson.toJson("No implementado"), HttpStatus.NOT_IMPLEMENTED);
	}
	
	
	
	
}
