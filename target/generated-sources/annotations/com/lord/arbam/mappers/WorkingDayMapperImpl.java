package com.lord.arbam.mappers;

import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.models.WorkingDay;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-11T17:20:35-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class WorkingDayMapperImpl implements WorkingDayMapper {

    @Override
    public WorkingDay toWorkingDay(WorkingDayDto workingDayDto) {
        if ( workingDayDto == null ) {
            return null;
        }

        WorkingDay.WorkingDayBuilder workingDay = WorkingDay.builder();

        workingDay.id( workingDayDto.getId() );
        workingDay.totalCash( workingDayDto.getTotalCash() );
        workingDay.totalDebit( workingDayDto.getTotalDebit() );
        workingDay.totalEmployeeSalary( workingDayDto.getTotalEmployeeSalary() );
        workingDay.totalPostEmployeeSalary( workingDayDto.getTotalPostEmployeeSalary() );
        workingDay.totalStartCash( workingDayDto.getTotalStartCash() );
        workingDay.totalTransf( workingDayDto.getTotalTransf() );
        workingDay.totalWorkingDay( workingDayDto.getTotalWorkingDay() );

        return workingDay.build();
    }

    @Override
    public WorkingDayDto toWorkingDayDto(WorkingDay workingDay) {
        if ( workingDay == null ) {
            return null;
        }

        WorkingDayDto workingDayDto = new WorkingDayDto();

        workingDayDto.setId( workingDay.getId() );
        workingDayDto.setTotalCash( workingDay.getTotalCash() );
        workingDayDto.setTotalDebit( workingDay.getTotalDebit() );
        workingDayDto.setTotalEmployeeSalary( workingDay.getTotalEmployeeSalary() );
        workingDayDto.setTotalPostEmployeeSalary( workingDay.getTotalPostEmployeeSalary() );
        workingDayDto.setTotalStartCash( workingDay.getTotalStartCash() );
        workingDayDto.setTotalTransf( workingDay.getTotalTransf() );
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
