package com.lord.arbam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.EmployeeDto;
import com.lord.arbam.dto.EmployeeJobDto;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;

@Mapper
public interface EmployeeMapper {

	public static EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
	
	@Mapping(target="employeeJob.jobRole", source="employeeJob")
	@Mapping(target="employeeJob.id", source="employeeJobId")
	@Mapping(target="employeeJob.employeeSalary", source="employeeSalary")
	public Employee toEmployee(EmployeeDto employeeDto);
	
	@Mapping(target="employeeJobId", source="employeeJob.id")
	@Mapping(target="employeeSalary", source="employeeJob.employeeSalary")
	@Mapping(target="employeeJob", source="employeeJob.jobRole")
	public EmployeeDto toEmployeeDto(Employee employee);
	
	@Mapping(target="employeeJobId", source="employeeJob.id")
	@Mapping(target="employeeSalary", source="employeeJob.employeeSalary")
	@Mapping(target="employeeJob", source="employeeJob.jobRole")
	public List<EmployeeDto> toEmployeesDto(List<Employee> employees);
	
	public EmployeeJob toEmployeeJob(EmployeeJobDto employeeJobDto);
	
	public EmployeeJobDto toEmployeeJobDto(EmployeeJob employeeJob);
	
	public List<EmployeeJobDto> toEmployeeJobsDto(List<EmployeeJob> jobs);
	
	
}
