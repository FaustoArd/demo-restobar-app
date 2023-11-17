package com.lord.arbam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.exceptions.EmployeeNotSelectedException;
import com.lord.arbam.mappers.WorkingDayMapper;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.EmployeeJob;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.EmployeeJobRepository;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.WorkingDayService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class WorkingDayServiceTest {
	
	@Autowired
	private WorkingDayService workingDayService;
	
	@Autowired
	private EmployeeJobRepository employeeJobRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	@BeforeEach
	public void setup() {
		EmployeeJob meseroJob = EmployeeJob.builder().jobRole("Mesera").build();
		EmployeeJob savedMeseroJob = employeeJobRepository.save(meseroJob); 
		
		EmployeeJob cajeroJob = EmployeeJob.builder().jobRole("Cajera").build(); 
		EmployeeJob savedCajeroJob = employeeJobRepository.save(cajeroJob); 
		
		EmployeeJob bacheroJob = EmployeeJob.builder().jobRole("Bachero").build(); 
		EmployeeJob savedBacheroJob = employeeJobRepository.save(bacheroJob); 
		
		EmployeeJob cocineroJob = EmployeeJob.builder().jobRole("Cocinero").build(); 
		EmployeeJob savedCocineroJob = employeeJobRepository.save(cocineroJob);
		
		Employee emp1 = Employee.builder().employeeName("Carla").employeeJob(savedMeseroJob).build();
		Employee savedEmp1 =  employeeService.saveEmployee(emp1);
		
		Employee emp2 = Employee.builder().employeeName("Marina").employeeJob(savedMeseroJob).build();
		Employee savedEmp2 =  employeeService.saveEmployee(emp2);
		
		Employee emp3 = Employee.builder().employeeName("Silvana").employeeJob(savedMeseroJob).build();
		Employee savedEmp3 =  employeeService.saveEmployee(emp3);
		
		Employee emp4 = Employee.builder().employeeName("Mirta").employeeJob(savedCajeroJob).build();
		Employee savedEmp4 =  employeeService.saveEmployee(emp4);
		
		Employee emp5 = Employee.builder().employeeName("Susana").employeeJob(savedBacheroJob).build();
		Employee savedEmp5 =  employeeService.saveEmployee(emp5);
	}
	
	@Test
	@Order(1)
	void WhenStartWorkingDayMethodIsCalled_MustReturnWorkingDayObject() {
		List<Employee> empsId = new ArrayList<>();
		Employee waitress1 = Employee.builder().id(1L).build();
		Employee waitress2 = Employee.builder().id(3L).build();
		Employee waitress3 = Employee.builder().id(2L).build();
		empsId.add(waitress1);
		empsId.add(waitress2);
		empsId.add(waitress3);
		WorkingDayDto workingDayDto = new WorkingDayDto();
		workingDayDto.setCashierName("Miguel");
		workingDayDto.setTotalStartCash(new BigDecimal(4500.00));
		workingDayDto.setEmployeesId(empsId);
		WorkingDay day = WorkingDayMapper.INSTANCE.toWorkingDayStart(workingDayDto);
		WorkingDay startedWorkingDay = workingDayService.startWorkingDay(day);
		assertTrue(startedWorkingDay.getId()!=null);
		assertEquals(startedWorkingDay.getCashierName(),"Miguel");
		assertEquals(startedWorkingDay.getTotalStartCash().doubleValue(),4500.00);
		assertEquals(startedWorkingDay.getWaitresses().stream().filter(wt -> wt.getId()==1L).findFirst().isPresent(),true);
		assertEquals(startedWorkingDay.getWaitresses().stream().filter(wt -> wt.getId()==3L).findFirst().isPresent(),true);
		assertEquals(startedWorkingDay.getWaitresses().stream().filter(wt -> wt.getId()==2L).findFirst().isPresent(),true);
		
		
	}
	@Test
	@Order(2)
	void deleteEmployeeFromWorkingDayTest() {
		long waitressId = 2L;
		long workingDayId = 1L;
		WorkingDay day =  workingDayService.deleteWaitressById(waitressId, workingDayId);
		assertEquals(day.getWaitresses().size(), 2);
		assertEquals(day.getWaitresses().stream().filter(wt -> wt.getId()==3L).findFirst().isPresent(), true);
		assertEquals(day.getWaitresses().stream().filter(wt -> wt.getId()==1L).findFirst().isPresent(), true);
		assertEquals(day.getWaitresses().stream().filter(wt -> wt.getId()==2L).findFirst().isPresent(), false);
		
	}
	
	/*@Test
	@Order(3)
	void WhenStartWorkingDayMethodIsCalledWithoutSelectingWaitresses_MustThrowException()throws Exception {
		
		RuntimeException exception = Assertions.assertThrows(EmployeeNotSelectedException.class, ()->{
			WorkingDayDto workingDayDto = new WorkingDayDto();
			workingDayDto.setCashierName("Miguel");
			workingDayDto.setTotalStartCash(new BigDecimal(4500.00));
			workingDayDto.setTotalPostEmployeeSalary(new BigDecimal(12500.00));
			WorkingDay day = WorkingDayMapper.INSTANCE.toWorkingDayStart(workingDayDto);
			workingDayService.startWorkingDay(day);
		},"Exception not throw");
		
		assertTrue(exception.getMessage().equals("Se debe seleccionar al menos una mesera"));
	}*/
	
	
}
