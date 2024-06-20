package com.lord.arbam.mapper;

import com.lord.arbam.dto.RestoTableOrderClosedDto;
import com.lord.arbam.model.RestoTableOrderClosed;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-18T23:52:21-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
public class RestoTableOrderClosedMapperImpl implements RestoTableOrderClosedMapper {

    @Override
    public RestoTableOrderClosed dtoToOrderClosed(RestoTableOrderClosedDto restoTableOrderClosedDto) {
        if ( restoTableOrderClosedDto == null ) {
            return null;
        }

        RestoTableOrderClosed.RestoTableOrderClosedBuilder restoTableOrderClosed = RestoTableOrderClosed.builder();

        restoTableOrderClosed.id( restoTableOrderClosedDto.getId() );
        restoTableOrderClosed.productQuantity( restoTableOrderClosedDto.getProductQuantity() );
        restoTableOrderClosed.totalOrderPrice( restoTableOrderClosedDto.getTotalOrderPrice() );
        restoTableOrderClosed.productName( restoTableOrderClosedDto.getProductName() );

        return restoTableOrderClosed.build();
    }

    @Override
    public RestoTableOrderClosedDto orderClosedToDto(RestoTableOrderClosed restoTableOrderClosed) {
        if ( restoTableOrderClosed == null ) {
            return null;
        }

        RestoTableOrderClosedDto restoTableOrderClosedDto = new RestoTableOrderClosedDto();

        restoTableOrderClosedDto.setId( restoTableOrderClosed.getId() );
        restoTableOrderClosedDto.setProductQuantity( restoTableOrderClosed.getProductQuantity() );
        restoTableOrderClosedDto.setTotalOrderPrice( restoTableOrderClosed.getTotalOrderPrice() );
        restoTableOrderClosedDto.setProductName( restoTableOrderClosed.getProductName() );

        return restoTableOrderClosedDto;
    }

    @Override
    public List<RestoTableOrderClosedDto> orderClosedToDtos(List<RestoTableOrderClosed> restoTableOrderCloseds) {
        if ( restoTableOrderCloseds == null ) {
            return null;
        }

        List<RestoTableOrderClosedDto> list = new ArrayList<RestoTableOrderClosedDto>( restoTableOrderCloseds.size() );
        for ( RestoTableOrderClosed restoTableOrderClosed : restoTableOrderCloseds ) {
            list.add( orderClosedToDto( restoTableOrderClosed ) );
        }

        return list;
    }
}
