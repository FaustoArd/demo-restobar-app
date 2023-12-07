package com.lord.arbam.controller;

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
import com.lord.arbam.dto.EmployeeDto;
import com.lord.arbam.dto.EmployeeJobDto;
import com.lord.arbam.exception.ValueDeletionInvalidException;
import com.lord.arbam.mapper.EmployeeMapper;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;
import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.service.EmployeeJobService;
import com.lord.arbam.service.EmployeeService;
import com.lord.arbam.service.WorkingDayService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/employees")
@RequiredArgsConstructor
public class EmployeeController {

	@Autowired
	private final EmployeeService employeeService;

	@Autowired
	private final EmployeeJobService employeeJobService;

	@Autowired
	private final WorkingDayService workingDayService;

	private static final Gson gson = new Gson();

	/** Employee **/
	@GetMapping("/all")
	ResponseEntity<List<EmployeeDto>> findAllemployeesByNameAsc() {
		List<Employee> employees = employeeService.findAllEmployeesSortByNameAsc();
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto, HttpStatus.OK);

	}

	@GetMapping("/employee/{id}")
	ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable("id") Long id) {
		Employee employee = employeeService.findEmployeeById(id);
		EmployeeDto employeeDto = EmployeeMapper.INSTANCE.toEmployeeDto(employee);
		return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.OK);
	}

	@GetMapping("/all_by_id")
	ResponseEntity<List<EmployeeDto>> findAllById(@RequestParam List<Long> employeesId) {
		List<Employee> employees = employeeService.findAllById(employeesId);
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto, HttpStatus.OK);
	}

	@GetMapping("/by_role")
	ResponseEntity<List<EmployeeDto>> findbyJobRole(@RequestParam String jobRole) {
		List<Employee> employees = employeeService.findByEmployeeJobJobRole(jobRole);
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto, HttpStatus.OK);
	}

	@PostMapping("/new_employee")
	ResponseEntity<String> createEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee employee = EmployeeMapper.INSTANCE.toEmployee(employeeDto);
		Employee savedEmployee = employeeService.saveEmployee(employee);
		return new ResponseEntity<String>(gson.toJson("Se guardo el empleado: " + savedEmployee.getEmployeeName()),
				HttpStatus.CREATED);

	}
	
	@PutMapping("/update_employee")
	ResponseEntity<String> updateEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee employee = EmployeeMapper.INSTANCE.toEmployee(employeeDto);
		Employee savedEmployee = employeeService.saveEmployee(employee);
		return new ResponseEntity<String>(gson.toJson("Se Actualizo  el empleado: " + savedEmployee.getEmployeeName()),
				HttpStatus.CREATED);

	}

	@DeleteMapping("/employee/{id}")
	ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id) {
		List<WorkingDay> workingDays = workingDayService.findByEmployeesId(id);
		if (workingDays.size() > 0) {
			return new ResponseEntity<String>(
					gson.toJson("No se puede eliminar el empleado porque ya pertenece a una jornada de trabajo"),
					HttpStatus.OK);
		}
		employeeService.deleteEmployeeById(id);
		return new ResponseEntity<String>(gson.toJson("Se elimino el empleado"), HttpStatus.OK);
	}

	/** Employee Job **/
	@GetMapping("/all_jobs")
	ResponseEntity<List<EmployeeJobDto>> findAllJobsOrderAsc() {
		List<EmployeeJob> jobs = employeeJobService.findAllbyJobRoleAsc();
		List<EmployeeJobDto> jobsDto = EmployeeMapper.INSTANCE.toEmployeeJobsDto(jobs);
		return new ResponseEntity<List<EmployeeJobDto>>(jobsDto, HttpStatus.OK);
	}

	@GetMapping("/employee_job/{id}")
	ResponseEntity<EmployeeJobDto> findEmployeeJobById(@PathVariable("id") Long id) {
		EmployeeJob job = employeeJobService.findJobById(id);
		EmployeeJobDto jobDto = EmployeeMapper.INSTANCE.toEmployeeJobDto(job);
		return new ResponseEntity<EmployeeJobDto>(jobDto, HttpStatus.OK);
	}

	@PostMapping("/new_job_role")
	ResponseEntity<EmployeeJobDto> createJobRole(@RequestBody EmployeeJobDto employeeJobDto) {
		EmployeeJob job = EmployeeMapper.INSTANCE.toEmployeeJob(employeeJobDto);
		EmployeeJob savedJob = employeeJobService.saveJob(job);
		EmployeeJobDto savedJobDto = EmployeeMapper.INSTANCE.toEmployeeJobDto(savedJob);
		return new ResponseEntity<EmployeeJobDto>(savedJobDto, HttpStatus.CREATED);
	}
	@PutMapping("/update_job_role")
	ResponseEntity<EmployeeJobDto> updateJobRole(@RequestBody EmployeeJobDto employeeJobDto) {
		EmployeeJob job = EmployeeMapper.INSTANCE.toEmployeeJob(employeeJobDto);
		EmployeeJob savedJob = employeeJobService.saveJob(job);
		EmployeeJobDto savedJobDto = EmployeeMapper.INSTANCE.toEmployeeJobDto(savedJob);
		return new ResponseEntity<EmployeeJobDto>(savedJobDto, HttpStatus.CREATED);
	}

	@DeleteMapping("/employee_job/{id}")
	ResponseEntity<String> deleteEmployeeJobById(@PathVariable("id") Long id) {

		employeeJobService.deleteJobById(id);
		return new ResponseEntity<String>(gson.toJson("Se elimino el Rol de trabajo"), HttpStatus.OK);
	}

}
