package com.lord.arbam.mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.WorkingDay;

@Mapper
public abstract class WorkingDayMapper {

	public static WorkingDayMapper INSTANCE = Mappers.getMapper(WorkingDayMapper.class);
	
	
	public  WorkingDay toWorkingDay(WorkingDayDto workingDayDto) {
		if(workingDayDto==null) {
			return null;
		}
		Set<Employee> employees = new HashSet<>();
		workingDayDto.getEmployeeId().forEach(emp ->{
			employees.add(new Employee(emp));
		});
		WorkingDay workingDay = WorkingDay.builder()
				.totalStartCash(workingDayDto.getTotalStartCash())
				.waitresses(employees)
				.cashierName(workingDayDto.getCashierName())
				.totalPostEmployeeSalary(workingDayDto.getTotalPostEmployeeSalary())
				.build();
				
		return workingDay;
		
	}
	
	@Mapping(target = "employeeId", ignore = true)
	public abstract WorkingDayDto toWorkingDayDto(WorkingDay workingDay);
	
	@Mapping(target = "employeeId", ignore = true)
	public  abstract List<WorkingDayDto> toWorkingDaysDto(List<WorkingDay> workingDays);
}
