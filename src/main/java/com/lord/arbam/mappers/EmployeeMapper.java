package com.lord.arbam.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dtos.EmployeeDto;
import com.lord.arbam.models.Employee;

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
	
	
}
