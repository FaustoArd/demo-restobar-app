package com.lord.arbam.mapper;

import com.lord.arbam.dto.PaymentMethodDto;
import com.lord.arbam.dto.RestoTableDto;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.RestoTable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T17:19:11-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class RestoTableMapperImpl implements RestoTableMapper {

    @Override
    public RestoTable toRestoTable(RestoTableDto restoTableDto) {
        if ( restoTableDto == null ) {
            return null;
        }

        RestoTable.RestoTableBuilder restoTable = RestoTable.builder();

        restoTable.employee( restoTableDtoToEmployee( restoTableDto ) );
        restoTable.id( restoTableDto.getId() );
        restoTable.tableNumber( restoTableDto.getTableNumber() );
        restoTable.totalTablePrice( restoTableDto.getTotalTablePrice() );
        restoTable.open( restoTableDto.isOpen() );

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
        restoTableDto.setId( restoTable.getId() );
        restoTableDto.setTableNumber( restoTable.getTableNumber() );
        restoTableDto.setTotalTablePrice( restoTable.getTotalTablePrice() );
        restoTableDto.setOpen( restoTable.isOpen() );

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

    @Override
    public List<PaymentMethodDto> toPaymentMethodsDto(List<PaymentMethod> methods) {
        if ( methods == null ) {
            return null;
        }

        List<PaymentMethodDto> list = new ArrayList<PaymentMethodDto>( methods.size() );
        for ( PaymentMethod paymentMethod : methods ) {
            list.add( paymentMethodToPaymentMethodDto( paymentMethod ) );
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

    protected PaymentMethodDto paymentMethodToPaymentMethodDto(PaymentMethod paymentMethod) {
        if ( paymentMethod == null ) {
            return null;
        }

        PaymentMethodDto paymentMethodDto = new PaymentMethodDto();

        paymentMethodDto.setId( paymentMethod.getId() );
        paymentMethodDto.setPaymentMethod( paymentMethod.getPaymentMethod() );

        return paymentMethodDto;
    }
}
