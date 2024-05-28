package com.lord.arbam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.model.RestoTableClosed;

@Mapper
public interface RestoTableClosedMapper {
	
	public static RestoTableClosedMapper INSTANCE = Mappers.getMapper(RestoTableClosedMapper.class);
	
	@Mapping(target = "ordersPaymentMethodDtos",ignore = true)
	@Mapping(target = "workingDayId",ignore = true)
	public RestoTableClosedDto toTableClosedDto(RestoTableClosed restoTableClosed);
	
	@Mapping(target="workingDayId",ignore = true)
	@Mapping(target="ordersPaymentMethodDtos",ignore = true)
	public List<RestoTableClosedDto> toTableClosedDtos(List<RestoTableClosed> tablesClosed);

}
