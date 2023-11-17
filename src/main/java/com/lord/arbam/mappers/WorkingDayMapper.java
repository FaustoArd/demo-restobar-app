package com.lord.arbam.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.exceptions.EmployeeNotSelectedException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.WorkingDay;

@Mapper
public abstract class WorkingDayMapper {

	public static WorkingDayMapper INSTANCE = Mappers.getMapper(WorkingDayMapper.class);

	public WorkingDay toWorkingDayStart(WorkingDayDto workingDayDto) {
		if (workingDayDto == null) {
			return null;
		}
		try {
			//List<Employee> employees = workingDayDto.getEmployeesId().stream().map(e -> e).collect(Collectors.toList());
			WorkingDay workingDay = WorkingDay.builder().id(workingDayDto.getId())
					.totalStartCash(workingDayDto.getTotalStartCash())
					.cashierName(workingDayDto.getCashierName())
					.totalPostEmployeeSalary(workingDayDto.getTotalPostEmployeeSalary()).build();
			
			return workingDay;
		} catch (RuntimeException e) {
			throw new EmployeeNotSelectedException("Se debe seleccionar al menos un mesero/a");
			
		}
		
		
	}

	@Mapping(target = "employeesId", ignore = true)
	public abstract WorkingDayDto toWorkingDayDto(WorkingDay workingDay);

	@Mapping(target = "employeesId", ignore = true)
	public abstract List<WorkingDayDto> toWorkingDaysDto(List<WorkingDay> workingDays);
}
