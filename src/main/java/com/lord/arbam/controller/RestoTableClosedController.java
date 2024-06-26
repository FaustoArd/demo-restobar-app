package com.lord.arbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dto.OrderPaymentMethodDto;
import com.lord.arbam.dto.OrderPaymentMethodResponse;
import com.lord.arbam.dto.PaymentMethodDto;
import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.dto.RestoTableOrderClosedDto;
import com.lord.arbam.mapper.RestoTableClosedMapper;
import com.lord.arbam.mapper.RestoTableOrderClosedMapper;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrderClosed;
import com.lord.arbam.service.PaymentMethodService;
import com.lord.arbam.service.RestoTableClosedService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/tables_closed")
@RequiredArgsConstructor
public class RestoTableClosedController {
	
	@Autowired
	private final  RestoTableClosedService restoTableClosedService;
	
	@Autowired
	private final PaymentMethodService paymentMethodService;
	
	@GetMapping("/all/{workingDayId}")
	ResponseEntity<List<RestoTableClosedDto>> findAllByTableNumberAsc(@PathVariable("workingDayId")Long workingDayId){
		List<RestoTableClosed> tablesClosed = restoTableClosedService.findAllByWorkingDayIdOrderByTableNumberAsc(workingDayId);
		List<RestoTableClosedDto> tablesClosedDto = RestoTableClosedMapper.INSTANCE.toTableClosedDtos(tablesClosed);
		return new ResponseEntity<List<RestoTableClosedDto>>(tablesClosedDto,HttpStatus.OK);
	}
	
	@GetMapping("/all-orders/{restoTableClosedId}")
	ResponseEntity<List<OrderPaymentMethodResponse>> findAllOrdersClosed(@PathVariable("restoTableClosedId")long restoTableClosedId){
		List<OrderPaymentMethodResponse> paymentMethodDtos = restoTableClosedService.findAllPaymentsByRestoTableClosed(restoTableClosedId);
		return ResponseEntity.ok(paymentMethodDtos);
	}
	
	@GetMapping("/all-payment-method")
	ResponseEntity<List<PaymentMethodDto>> findAllPaymentMethods(){
		List<PaymentMethodDto> dtos = paymentMethodService.findAllPaymentMethods();
		return ResponseEntity.ok(dtos);
	}
	
	@PutMapping("/update-payment-method")
	ResponseEntity<PaymentMethodDto> updatePaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto){
		PaymentMethodDto dto = paymentMethodService.updatePaymentMethod(paymentMethodDto);
		return new ResponseEntity<PaymentMethodDto>(dto,HttpStatus.OK);
	}
	

}
