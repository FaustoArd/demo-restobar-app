package com.lord.arbam.mapper;

import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.model.RestoTableClosed;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T19:15:17-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class RestoTableClosedMapperImpl implements RestoTableClosedMapper {

    @Override
    public RestoTableClosedDto toTableClosedDto(RestoTableClosed restoTableClosed) {
        if ( restoTableClosed == null ) {
            return null;
        }

        RestoTableClosedDto restoTableClosedDto = new RestoTableClosedDto();

        restoTableClosedDto.setEmployeeName( restoTableClosed.getEmployeeName() );
        restoTableClosedDto.setId( restoTableClosed.getId() );
        restoTableClosedDto.setTableNumber( restoTableClosed.getTableNumber() );
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
