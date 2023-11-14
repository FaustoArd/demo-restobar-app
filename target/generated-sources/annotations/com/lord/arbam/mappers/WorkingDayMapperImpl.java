package com.lord.arbam.mappers;

import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.models.WorkingDay;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-14T17:23:44-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class WorkingDayMapperImpl extends WorkingDayMapper {

    @Override
    public WorkingDayDto toWorkingDayDto(WorkingDay workingDay) {
        if ( workingDay == null ) {
            return null;
        }

        WorkingDayDto workingDayDto = new WorkingDayDto();

        workingDayDto.setCashierName( workingDay.getCashierName() );
        workingDayDto.setId( workingDay.getId() );
        workingDayDto.setTotalCash( workingDay.getTotalCash() );
        workingDayDto.setTotalCashierSalary( workingDay.getTotalCashierSalary() );
        workingDayDto.setTotalDebit( workingDay.getTotalDebit() );
        workingDayDto.setTotalPostEmployeeSalary( workingDay.getTotalPostEmployeeSalary() );
        workingDayDto.setTotalStartCash( workingDay.getTotalStartCash() );
        workingDayDto.setTotalTransf( workingDay.getTotalTransf() );
        workingDayDto.setTotalWaitressSalary( workingDay.getTotalWaitressSalary() );
        workingDayDto.setTotalWorkingDay( workingDay.getTotalWorkingDay() );

        return workingDayDto;
    }

    @Override
    public List<WorkingDayDto> toWorkingDaysDto(List<WorkingDay> workingDays) {
        if ( workingDays == null ) {
            return null;
        }

        List<WorkingDayDto> list = new ArrayList<WorkingDayDto>( workingDays.size() );
        for ( WorkingDay workingDay : workingDays ) {
            list.add( toWorkingDayDto( workingDay ) );
        }

        return list;
    }
}
