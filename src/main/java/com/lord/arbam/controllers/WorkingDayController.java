package com.lord.arbam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.mappers.WorkingDayMapper;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.services.WorkingDayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/working_days")
@RequiredArgsConstructor
public class WorkingDayController {
	
	@Autowired
	private final WorkingDayService workingDayService;
	
	@GetMapping("/{id}")
	ResponseEntity<WorkingDayDto> findWorkingDayById(@PathVariable("id")Long id){
		WorkingDay day = workingDayService.findWorkingDayById(id);
		WorkingDayDto dayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(day);
		return new ResponseEntity<WorkingDayDto>(dayDto,HttpStatus.OK);
	}

	@PostMapping("/")
	ResponseEntity<WorkingDayDto> startWorkingDay(@RequestBody WorkingDayDto workingDayDto){
		WorkingDay workingDay = WorkingDayMapper.INSTANCE.toWorkingDayStart(workingDayDto);
		WorkingDay savedWorkingDay = workingDayService.startWorkingDay(workingDay);
		WorkingDayDto savedWorkingDayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(savedWorkingDay);
		return new ResponseEntity<WorkingDayDto>(savedWorkingDayDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	ResponseEntity<WorkingDayDto> updateWorkingDay(@RequestBody WorkingDayDto workingDayDto){
		WorkingDay workingDay = WorkingDayMapper.INSTANCE.toWorkingDayStart(workingDayDto);
		WorkingDay updatedWorkingDay = workingDayService.updateWorkingDay(workingDay);
		WorkingDayDto updatedWorkingDayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(updatedWorkingDay);
		return new ResponseEntity<WorkingDayDto>(updatedWorkingDayDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/close/{id}")
	ResponseEntity<WorkingDayDto> closeWorkingDay(@PathVariable("id") Long id){
		WorkingDay workingDay = workingDayService.closeWorkingDay(id);
		WorkingDayDto workingDayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(workingDay);
		return new ResponseEntity<WorkingDayDto>(workingDayDto,HttpStatus.OK);
	}
}
