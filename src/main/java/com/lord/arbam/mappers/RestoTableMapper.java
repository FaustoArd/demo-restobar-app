package com.lord.arbam.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dtos.RestoTableDto;
import com.lord.arbam.models.RestoTable;

@Mapper
public interface RestoTableMapper {
	
	public static RestoTableMapper INSTANCE = Mappers.getMapper(RestoTableMapper.class);
	
	@Mapping(target="employee.id", source="employeeId")
	@Mapping(target="employee.employeeName", source="employeeName")
	public RestoTable toRestoTable(RestoTableDto restoTableDto);
	
	@Mapping(target="employeeId", source="employee.id")
	@Mapping(target="employeeName", source="employee.employeeName")
	public RestoTableDto toRestotableDto(RestoTable restoTable);
	
	@Mapping(target="employeeId", source="employee.id")
	@Mapping(target="employeeName", source="employee.employeeName")
	public List<RestoTableDto> toRestoTablesDto(List<RestoTable> restoTables);

}
