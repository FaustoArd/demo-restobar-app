package com.lord.arbam.mapper;

import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.model.RestoTableClosed;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-18T23:52:21-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
public class RestoTableClosedMapperImpl implements RestoTableClosedMapper {

    @Override
    public RestoTableClosedDto toTableClosedDto(RestoTableClosed restoTableClosed) {
        if ( restoTableClosed == null ) {
            return null;
        }

        RestoTableClosedDto restoTableClosedDto = new RestoTableClosedDto();

        restoTableClosedDto.setId( restoTableClosed.getId() );
        restoTableClosedDto.setTableNumber( restoTableClosed.getTableNumber() );
        restoTableClosedDto.setEmployeeName( restoTableClosed.getEmployeeName() );
        restoTableClosedDto.setTotalPrice( restoTableClosed.getTotalPrice() );

        return restoTableClosedDto;
    }

    @Override
    public List<RestoTableClosedDto> toTableClosedDtos(List<RestoTableClosed> tablesClosed) {
        if ( tablesClosed == null ) {
            return null;
        }

        List<RestoTableClosedDto> list = new ArrayList<RestoTableClosedDto>( tablesClosed.size() );
        for ( RestoTableClosed restoTableClosed : tablesClosed ) {
            list.add( toTableClosedDto( restoTableClosed ) );
        }

        return list;
    }
}
