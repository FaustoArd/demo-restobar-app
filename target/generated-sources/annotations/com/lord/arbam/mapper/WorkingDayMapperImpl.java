package com.lord.arbam.mapper;

import com.lord.arbam.dto.WorkingDayDto;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.WorkingDay;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T17:19:11-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class WorkingDayMapperImpl implements WorkingDayMapper {

    @Override
    public WorkingDay toWorkingDay(WorkingDayDto workingDayDto) {
        if ( workingDayDto == null ) {
            return null;
        }

        WorkingDay.WorkingDayBuilder workingDay = WorkingDay.builder();

        workingDay.id( workingDayDto.getId() );
        workingDay.date( workingDayDto.getDate() );
        workingDay.totalStartCash( workingDayDto.getTotalStartCash() );
        workingDay.totalWorkingDay( workingDayDto.getTotalWorkingDay() );
        workingDay.totalCash( workingDayDto.getTotalCash() );
        workingDay.totalWorkingDayWithDiscount( workingDayDto.getTotalWorkingDayWithDiscount() );
        workingDay.totalDebit( workingDayDto.getTotalDebit() );
        workingDay.totalCredit( workingDayDto.getTotalCredit() );
        workingDay.totalMP( workingDayDto.getTotalMP() );
        workingDay.totalTransf( workingDayDto.getTotalTransf() );
        workingDay.totalEmployeeSalary( workingDayDto.getTotalEmployeeSalary() );
        List<Employee> list = workingDayDto.getEmployees();
        if ( list != null ) {
            workingDay.employees( new ArrayList<Employee>( list ) );
        }
        workingDay.dayStarted( workingDayDto.isDayStarted() );

        return workingDay.build();
    }

    @Override
    public WorkingDayDto toWorkingDayDto(WorkingDay workingDay) {
        if ( workingDay == null ) {
            return null;
        }

        WorkingDayDto workingDayDto = new WorkingDayDto();

        workingDayDto.setId( workingDay.getId() );
        workingDayDto.setDate( workingDay.getDate() );
        workingDayDto.setTotalStartCash( workingDay.getTotalStartCash() );
        workingDayDto.setTotalWorkingDay( workingDay.getTotalWorkingDay() );
        workingDayDto.setTotalCash( workingDay.getTotalCash() );
        workingDayDto.setTotalWorkingDayWithDiscount( workingDay.getTotalWorkingDayWithDiscount() );
        workingDayDto.setTotalDebit( workingDay.getTotalDebit() );
        workingDayDto.setTotalTransf( workingDay.getTotalTransf() );
        workingDayDto.setTotalCredit( workingDay.getTotalCredit() );
        workingDayDto.setTotalMP( workingDay.getTotalMP() );
        workingDayDto.setTotalEmployeeSalary( workingDay.getTotalEmployeeSalary() );
        List<Employee> list = workingDay.getEmployees();
        if ( list != null ) {
            workingDayDto.setEmployees( new ArrayList<Employee>( list ) );
        }
        workingDayDto.setDayStarted( workingDay.isDayStarted() );

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
