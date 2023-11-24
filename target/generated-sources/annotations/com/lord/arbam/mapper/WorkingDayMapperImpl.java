package com.lord.arbam.mapper;

import com.lord.arbam.dto.WorkingDayDto;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.WorkingDay;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-23T22:16:38-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class WorkingDayMapperImpl implements WorkingDayMapper {

    @Override
    public WorkingDay toWorkingDay(WorkingDayDto workingDayDto) {
        if ( workingDayDto == null ) {
            return null;
        }

        WorkingDay.WorkingDayBuilder workingDay = WorkingDay.builder();

        workingDay.dayStarted( workingDayDto.isDayStarted() );
        List<Employee> list = workingDayDto.getEmployees();
        if ( list != null ) {
            workingDay.employees( new ArrayList<Employee>( list ) );
        }
        workingDay.id( workingDayDto.getId() );
        workingDay.totalCash( workingDayDto.getTotalCash() );
        workingDay.totalCashWithDiscount( workingDayDto.getTotalCashWithDiscount() );
        workingDay.totalCredit( workingDayDto.getTotalCredit() );
        workingDay.totalDebit( workingDayDto.getTotalDebit() );
        workingDay.totalEmployeeSalary( workingDayDto.getTotalEmployeeSalary() );
        workingDay.totalMP( workingDayDto.getTotalMP() );
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

        workingDayDto.setDayStarted( workingDay.isDayStarted() );
        List<Employee> list = workingDay.getEmployees();
        if ( list != null ) {
            workingDayDto.setEmployees( new ArrayList<Employee>( list ) );
        }
        workingDayDto.setId( workingDay.getId() );
        workingDayDto.setTotalCash( workingDay.getTotalCash() );
        workingDayDto.setTotalCashWithDiscount( workingDay.getTotalCashWithDiscount() );
        workingDayDto.setTotalCredit( workingDay.getTotalCredit() );
        workingDayDto.setTotalDebit( workingDay.getTotalDebit() );
        workingDayDto.setTotalEmployeeSalary( workingDay.getTotalEmployeeSalary() );
        workingDayDto.setTotalMP( workingDay.getTotalMP() );
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
