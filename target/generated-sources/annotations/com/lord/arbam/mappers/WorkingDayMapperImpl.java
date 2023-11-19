package com.lord.arbam.mappers;

import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.WorkingDay;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-19T02:36:57-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class WorkingDayMapperImpl implements WorkingDayMapper {

    @Override
    public WorkingDay toWorkingDay(WorkingDayDto workingDayDto) {
        if ( workingDayDto == null ) {
            return null;
        }

        WorkingDay.WorkingDayBuilder workingDay = WorkingDay.builder();

        workingDay.cashierName( workingDayDto.getCashierName() );
        workingDay.dayStarted( workingDayDto.isDayStarted() );
        workingDay.id( workingDayDto.getId() );
        workingDay.totalCash( workingDayDto.getTotalCash() );
        workingDay.totalCashierSalary( workingDayDto.getTotalCashierSalary() );
        workingDay.totalChefSalary( workingDayDto.getTotalChefSalary() );
        workingDay.totalDebit( workingDayDto.getTotalDebit() );
        workingDay.totalDishWasherSalary( workingDayDto.getTotalDishWasherSalary() );
        workingDay.totalHelperSalary( workingDayDto.getTotalHelperSalary() );
        workingDay.totalStartCash( workingDayDto.getTotalStartCash() );
        workingDay.totalTransf( workingDayDto.getTotalTransf() );
        workingDay.totalWaitressSalary( workingDayDto.getTotalWaitressSalary() );
        workingDay.totalWorkingDay( workingDayDto.getTotalWorkingDay() );
        List<Employee> list = workingDayDto.getWaitresses();
        if ( list != null ) {
            workingDay.waitresses( new ArrayList<Employee>( list ) );
        }

        return workingDay.build();
    }

    @Override
    public WorkingDayDto toWorkingDayDto(WorkingDay workingDay) {
        if ( workingDay == null ) {
            return null;
        }

        WorkingDayDto workingDayDto = new WorkingDayDto();

        workingDayDto.setCashierName( workingDay.getCashierName() );
        workingDayDto.setDayStarted( workingDay.isDayStarted() );
        workingDayDto.setId( workingDay.getId() );
        workingDayDto.setTotalCash( workingDay.getTotalCash() );
        workingDayDto.setTotalCashierSalary( workingDay.getTotalCashierSalary() );
        workingDayDto.setTotalChefSalary( workingDay.getTotalChefSalary() );
        workingDayDto.setTotalDebit( workingDay.getTotalDebit() );
        workingDayDto.setTotalDishWasherSalary( workingDay.getTotalDishWasherSalary() );
        workingDayDto.setTotalHelperSalary( workingDay.getTotalHelperSalary() );
        workingDayDto.setTotalStartCash( workingDay.getTotalStartCash() );
        workingDayDto.setTotalTransf( workingDay.getTotalTransf() );
        workingDayDto.setTotalWaitressSalary( workingDay.getTotalWaitressSalary() );
        workingDayDto.setTotalWorkingDay( workingDay.getTotalWorkingDay() );
        List<Employee> list = workingDay.getWaitresses();
        if ( list != null ) {
            workingDayDto.setWaitresses( new ArrayList<Employee>( list ) );
        }

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
