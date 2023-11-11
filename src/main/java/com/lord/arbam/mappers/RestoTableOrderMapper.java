package com.lord.arbam.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dtos.RestoTableOrderDto;
import com.lord.arbam.models.RestoTableOrder;

@Mapper
public interface RestoTableOrderMapper {

	public static RestoTableOrderMapper INSTANCE = Mappers.getMapper(RestoTableOrderMapper.class);
	
	@Mapping(target="product.id", source="productId")
	@Mapping(target="product.productName", source="productName")
	@Mapping(target="restoTable.id", source="restoTableId")
	public RestoTableOrder toOrder(RestoTableOrderDto restoTableOrderDto);
	
	@Mapping(target="productId", source="product.id")
	@Mapping(target="productName", source="product.productName")
	@Mapping(target="restoTableId", source="restoTable.id")
	public RestoTableOrderDto toOrderDto(RestoTableOrder restoTableOrder);
	
	public List<RestoTableOrderDto> toOrdersDto(List<RestoTableOrder> orders);
}
