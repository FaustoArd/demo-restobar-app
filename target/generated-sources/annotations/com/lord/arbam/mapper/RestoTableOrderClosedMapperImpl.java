package com.lord.arbam.mapper;

import com.lord.arbam.dto.RestoTableOrderClosedDto;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrderClosed;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-24T01:21:30-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class RestoTableOrderClosedMapperImpl implements RestoTableOrderClosedMapper {

    @Override
    public RestoTableOrderClosedDto orderClosedToDto(RestoTableOrderClosed restoTableOrderClosed) {
        if ( restoTableOrderClosed == null ) {
            return null;
        }

        RestoTableOrderClosedDto restoTableOrderClosedDto = new RestoTableOrderClosedDto();

        Long id = restoTableOrderClosedRestoTableClosedId( restoTableOrderClosed );
        if ( id != null ) {
            restoTableOrderClosedDto.setRestoTableClosedId( id );
        }
        restoTableOrderClosedDto.setId( restoTableOrderClosed.getId() );
        restoTableOrderClosedDto.setProductName( restoTableOrderClosed.getProductName() );
        restoTableOrderClosedDto.setProductQuantity( restoTableOrderClosed.getProductQuantity() );
        restoTableOrderClosedDto.setTotalOrderPrice( restoTableOrderClosed.getTotalOrderPrice() );

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

    private Long restoTableOrderClosedRestoTableClosedId(RestoTableOrderClosed restoTableOrderClosed) {
        if ( restoTableOrderClosed == null ) {
            return null;
        }
        RestoTableClosed restoTableClosed = restoTableOrderClosed.getRestoTableClosed();
        if ( restoTableClosed == null ) {
            return null;
        }
        Long id = restoTableClosed.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
