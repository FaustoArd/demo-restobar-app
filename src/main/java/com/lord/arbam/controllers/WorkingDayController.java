package com.lord.arbam.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.EmployeeDto;
import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.mappers.EmployeeMapper;
import com.lord.arbam.mappers.WorkingDayMapper;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.services.WorkingDayService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/working_days")
@RequiredArgsConstructor
public class WorkingDayController {
	
	@Autowired
	private final WorkingDayService workingDayService;
	
	private static final Gson gson = new Gson();
	
	@GetMapping("/{id}")
	ResponseEntity<WorkingDayDto> findWorkingDayById(@PathVariable("id")Long id){
		WorkingDay day = workingDayService.findWorkingDayById(id);
		WorkingDayDto dayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(day);
		return new ResponseEntity<WorkingDayDto>(dayDto,HttpStatus.OK);
	}

	@PostMapping("/")
	ResponseEntity<WorkingDayDto> startWorkingDay(@RequestBody WorkingDayDto workingDayDto){
		WorkingDay workingDay = WorkingDayMapper.INSTANCE.toWorkingDay(workingDayDto);
		WorkingDay savedWorkingDay = workingDayService.startWorkingDay(workingDay);
		WorkingDayDto savedWorkingDayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(savedWorkingDay);
		return new ResponseEntity<WorkingDayDto>(savedWorkingDayDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	ResponseEntity<WorkingDayDto> updateWorkingDay(@RequestBody WorkingDayDto workingDayDto){
		
		WorkingDay workingDay = WorkingDayMapper.INSTANCE.toWorkingDay(workingDayDto);
		WorkingDay updatedWorkingDay = workingDayService.updateWorkingDay(workingDay);
		WorkingDayDto updatedWorkingDayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(updatedWorkingDay);
		return new ResponseEntity<WorkingDayDto>(updatedWorkingDayDto,HttpStatus.OK);
	}
	
	@GetMapping("/close/{id}")
	ResponseEntity<WorkingDayDto> closeWorkingDay(@PathVariable("id") Long id){
		WorkingDay workingDay = workingDayService.closeWorkingDay(id);
		WorkingDayDto workingDayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(workingDay);
		return new ResponseEntity<WorkingDayDto>(workingDayDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/employees")
	ResponseEntity<WorkingDayDto> deleteEmployeeById(@RequestParam Long employeesId,@RequestParam Long workingDayId){
		WorkingDay day = workingDayService.deleteEmployeesById(employeesId, workingDayId);
		WorkingDayDto dayDto = WorkingDayMapper.INSTANCE.toWorkingDayDto(day);
		return new ResponseEntity<WorkingDayDto>(dayDto,HttpStatus.OK);
	}
	@GetMapping("/is_started")
	ResponseEntity<Boolean> isDayStarted(@RequestParam("workingDayId") Long workingDayId) {
		return new ResponseEntity<Boolean>(workingDayService.isWorkingDayStarted(workingDayId),HttpStatus.OK);
	}
	@GetMapping("/find_employees"
			+ "")
	ResponseEntity<List<EmployeeDto>> findCurrentEmployees(@RequestParam Long workingDayId){
		List<Employee> employees = workingDayService.findCurrentEmployeesSelected(workingDayId);
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto,HttpStatus.OK);
	}
}
