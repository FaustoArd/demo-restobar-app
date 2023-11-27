package com.lord.arbam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.model.RestoTableClosed;

@Mapper
public interface RestoTableClosedMapper {
	
	public static RestoTableClosedMapper INSTANCE = Mappers.getMapper(RestoTableClosedMapper.class);
	
	public List<RestoTableClosedDto> toTableClosedDto(List<RestoTableClosed> tablesClosed);

}
