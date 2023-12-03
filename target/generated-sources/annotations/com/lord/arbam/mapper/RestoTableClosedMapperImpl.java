package com.lord.arbam.mapper;

import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.model.RestoTableClosed;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-02T10:36:21-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class RestoTableClosedMapperImpl implements RestoTableClosedMapper {

    @Override
    public List<RestoTableClosedDto> toTableClosedDto(List<RestoTableClosed> tablesClosed) {
        if ( tablesClosed == null ) {
            return null;
        }

        List<RestoTableClosedDto> list = new ArrayList<RestoTableClosedDto>( tablesClosed.size() );
        for ( RestoTableClosed restoTableClosed : tablesClosed ) {
            list.add( restoTableClosedToRestoTableClosedDto( restoTableClosed ) );
        }

        return list;
    }

    protected RestoTableClosedDto restoTableClosedToRestoTableClosedDto(RestoTableClosed restoTableClosed) {
        if ( restoTableClosed == null ) {
            return null;
        }

        RestoTableClosedDto restoTableClosedDto = new RestoTableClosedDto();

        restoTableClosedDto.setId( restoTableClosed.getId() );
        restoTableClosedDto.setTableNumber( restoTableClosed.getTableNumber() );
        restoTableClosedDto.setEmployeeName( restoTableClosed.getEmployeeName() );
        restoTableClosedDto.setTotalPrice( restoTableClosed.getTotalPrice() );
        restoTableClosedDto.setPaymentMethod( restoTableClosed.getPaymentMethod() );

        return restoTableClosedDto;
    }
}
