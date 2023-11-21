package com.lord.arbam.mappers;

import com.lord.arbam.dtos.EmployeeDto;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.EmployeeJob;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T20:15:45-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEmployee(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.employeeJob( employeeDtoToEmployeeJob( employeeDto ) );
        employee.employeeName( employeeDto.getEmployeeName() );
        employee.id( employeeDto.getId() );

        return employee.build();
    }

    @Override
    public EmployeeDto toEmployeeDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setEmployeeJobId( employeeEmployeeJobId( employee ) );
        BigDecimal employeeSalary = employeeEmployeeJobEmployeeSalary( employee );
        if ( employeeSalary != null ) {
            employeeDto.setEmployeeSalary( employeeSalary.toString() );
        }
        employeeDto.setEmployeeJob( employeeEmployeeJobJobRole( employee ) );
        employeeDto.setEmployeeName( employee.getEmployeeName() );
        employeeDto.setId( employee.getId() );

        return employeeDto;
    }

    @Override
    public List<EmployeeDto> toEmployeesDto(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeDto> list = new ArrayList<EmployeeDto>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( toEmployeeDto( employee ) );
        }

        return list;
    }

    protected EmployeeJob employeeDtoToEmployeeJob(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        EmployeeJob.EmployeeJobBuilder employeeJob = EmployeeJob.builder();

        employeeJob.jobRole( employeeDto.getEmployeeJob() );
        employeeJob.id( employeeDto.getEmployeeJobId() );
        if ( employeeDto.getEmployeeSalary() != null ) {
            employeeJob.employeeSalary( new BigDecimal( employeeDto.getEmployeeSalary() ) );
        }

        return employeeJob.build();
    }

    private Long employeeEmployeeJobId(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        EmployeeJob employeeJob = employee.getEmployeeJob();
        if ( employeeJob == null ) {
            return null;
        }
        Long id = employeeJob.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private BigDecimal employeeEmployeeJobEmployeeSalary(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        EmployeeJob employeeJob = employee.getEmployeeJob();
        if ( employeeJob == null ) {
            return null;
        }
        BigDecimal employeeSalary = employeeJob.getEmployeeSalary();
        if ( employeeSalary == null ) {
            return null;
        }
        return employeeSalary;
    }

    private String employeeEmployeeJobJobRole(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        EmployeeJob employeeJob = employee.getEmployeeJob();
        if ( employeeJob == null ) {
            return null;
        }
        String jobRole = employeeJob.getJobRole();
        if ( jobRole == null ) {
            return null;
        }
        return jobRole;
    }
}
