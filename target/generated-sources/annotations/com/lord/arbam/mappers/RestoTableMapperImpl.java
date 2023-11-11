package com.lord.arbam.mappers;

import com.lord.arbam.dtos.RestoTableDto;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-11T09:28:55-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class RestoTableMapperImpl implements RestoTableMapper {

    @Override
    public RestoTable toRestoTable(RestoTableDto restoTableDto) {
        if ( restoTableDto == null ) {
            return null;
        }

        RestoTable.RestoTableBuilder restoTable = RestoTable.builder();

        restoTable.employee( restoTableDtoToEmployee( restoTableDto ) );
        restoTable.tableOrder( restoTableDtoToRestoTableOrder( restoTableDto ) );
        restoTable.closeTime( restoTableDto.getCloseTime() );
        restoTable.id( restoTableDto.getId() );
        restoTable.open( restoTableDto.isOpen() );
        restoTable.tableNumber( restoTableDto.getTableNumber() );
        restoTable.totalTablePrice( restoTableDto.getTotalTablePrice() );

        return restoTable.build();
    }

    @Override
    public RestoTableDto toRestotableDto(RestoTable restoTable) {
        if ( restoTable == null ) {
            return null;
        }

        RestoTableDto restoTableDto = new RestoTableDto();

        restoTableDto.setEmployeeId( restoTableEmployeeId( restoTable ) );
        restoTableDto.setEmployeeName( restoTableEmployeeEmployeeName( restoTable ) );
        restoTableDto.setTableOrderId( restoTableTableOrderId( restoTable ) );
        restoTableDto.setCloseTime( restoTable.getCloseTime() );
        restoTableDto.setId( restoTable.getId() );
        restoTableDto.setOpen( restoTable.isOpen() );
        restoTableDto.setTableNumber( restoTable.getTableNumber() );
        restoTableDto.setTotalTablePrice( restoTable.getTotalTablePrice() );

        return restoTableDto;
    }

    @Override
    public List<RestoTableDto> toRestoTablesDto(List<RestoTable> restoTables) {
        if ( restoTables == null ) {
            return null;
        }

        List<RestoTableDto> list = new ArrayList<RestoTableDto>( restoTables.size() );
        for ( RestoTable restoTable : restoTables ) {
            list.add( toRestotableDto( restoTable ) );
        }

        return list;
    }

    protected Employee restoTableDtoToEmployee(RestoTableDto restoTableDto) {
        if ( restoTableDto == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.id( restoTableDto.getEmployeeId() );
        employee.employeeName( restoTableDto.getEmployeeName() );

        return employee.build();
    }

    protected RestoTableOrder restoTableDtoToRestoTableOrder(RestoTableDto restoTableDto) {
        if ( restoTableDto == null ) {
            return null;
        }

        RestoTableOrder.RestoTableOrderBuilder restoTableOrder = RestoTableOrder.builder();

        restoTableOrder.id( restoTableDto.getTableOrderId() );

        return restoTableOrder.build();
    }

    private Long restoTableEmployeeId(RestoTable restoTable) {
        if ( restoTable == null ) {
            return null;
        }
        Employee employee = restoTable.getEmployee();
        if ( employee == null ) {
            return null;
        }
        Long id = employee.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String restoTableEmployeeEmployeeName(RestoTable restoTable) {
        if ( restoTable == null ) {
            return null;
        }
        Employee employee = restoTable.getEmployee();
        if ( employee == null ) {
            return null;
        }
        String employeeName = employee.getEmployeeName();
        if ( employeeName == null ) {
            return null;
        }
        return employeeName;
    }

    private Long restoTableTableOrderId(RestoTable restoTable) {
        if ( restoTable == null ) {
            return null;
        }
        RestoTableOrder tableOrder = restoTable.getTableOrder();
        if ( tableOrder == null ) {
            return null;
        }
        Long id = tableOrder.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
