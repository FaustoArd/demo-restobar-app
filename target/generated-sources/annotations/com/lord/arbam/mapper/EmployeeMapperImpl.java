package com.lord.arbam.mapper;

import com.lord.arbam.dto.EmployeeDto;
import com.lord.arbam.dto.EmployeeJobDto;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-29T18:20:07-0300",
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
            employeeDto.setEmployeeSalary( employeeSalary.toBigInteger() );
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

    @Override
    public EmployeeJob toEmployeeJob(EmployeeJobDto employeeJobDto) {
        if ( employeeJobDto == null ) {
            return null;
        }

        EmployeeJob.EmployeeJobBuilder employeeJob = EmployeeJob.builder();

        employeeJob.employeeSalary( employeeJobDto.getEmployeeSalary() );
        employeeJob.id( employeeJobDto.getId() );
        employeeJob.jobRole( employeeJobDto.getJobRole() );

        return employeeJob.build();
    }

    @Override
    public EmployeeJobDto toEmployeeJobDto(EmployeeJob employeeJob) {
        if ( employeeJob == null ) {
            return null;
        }

        EmployeeJobDto employeeJobDto = new EmployeeJobDto();

        employeeJobDto.setEmployeeSalary( employeeJob.getEmployeeSalary() );
        employeeJobDto.setId( employeeJob.getId() );
        employeeJobDto.setJobRole( employeeJob.getJobRole() );

        return employeeJobDto;
    }

    @Override
    public List<EmployeeJobDto> toEmployeeJobsDto(List<EmployeeJob> jobs) {
        if ( jobs == null ) {
            return null;
        }

        List<EmployeeJobDto> list = new ArrayList<EmployeeJobDto>( jobs.size() );
        for ( EmployeeJob employeeJob : jobs ) {
            list.add( toEmployeeJobDto( employeeJob ) );
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
