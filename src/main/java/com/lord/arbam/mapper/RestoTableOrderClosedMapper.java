package com.lord.arbam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.RestoTableOrderClosedDto;
import com.lord.arbam.model.RestoTableOrderClosed;

@Mapper
public interface RestoTableOrderClosedMapper {
	
	public static RestoTableOrderClosedMapper INSTANCE = Mappers.getMapper(RestoTableOrderClosedMapper.class);
	
	@Mapping(target="restoTableClosedId",ignore = true)
	public RestoTableOrderClosedDto orderClosedToDto(RestoTableOrderClosed restoTableOrderClosed);
	
	@Mapping(target="ordersPaymentMethoDtos",ignore=true)
	public List<RestoTableOrderClosedDto> orderClosedToDtos(List<RestoTableOrderClosed> restoTableOrderCloseds);

}
