package com.lord.arbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.mapper.RestoTableClosedMapper;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.service.RestoTableClosedService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/tables_closed")
@RequiredArgsConstructor
public class RestoTableClosedController {
	
	@Autowired
	private final  RestoTableClosedService restoTableClosedService;
	
	@GetMapping("/all/{workingDayId}")
	ResponseEntity<List<RestoTableClosedDto>> findAllByTableNumberAsc(@PathVariable("workingDayId")Long workingDayId){
		List<RestoTableClosed> tablesClosed = restoTableClosedService.findAllByWorkingDayIdOrderByTableNumberAsc(workingDayId);
		List<RestoTableClosedDto> tablesClosedDto = RestoTableClosedMapper.INSTANCE.toTableClosedDto(tablesClosed);
		return new ResponseEntity<List<RestoTableClosedDto>>(tablesClosedDto,HttpStatus.OK);
	}
	
	

}
