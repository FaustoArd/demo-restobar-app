package com.lord.arbam;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.mappers.WorkingDayMapper;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.EmployeeJob;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.EmployeeJobRepository;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.WorkingDayService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class WorkingDayServiceTest {
	
	@Autowired
	private WorkingDayService workingDayService;
	
	@Autowired
	private EmployeeJobRepository employeeJobRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	private Long workingDayId;
	
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
		workingDayDto.setTotalStartCash(new BigDecimal(10000.00));
		workingDayDto.setEmployees(empsId);
	
		
		WorkingDay day = WorkingDayMapper.INSTANCE.toWorkingDay(workingDayDto);
		WorkingDay startedWorkingDay = workingDayService.startWorkingDay(day);
		this.workingDayId = startedWorkingDay.getId();
		assertTrue(startedWorkingDay.getId()!=null);
		assertTrue(startedWorkingDay.isDayStarted());
		assertEquals(startedWorkingDay.getCashierName(),"Miguel");
		assertEquals(startedWorkingDay.getTotalStartCash().doubleValue(),10000.00);
		assertTrue(startedWorkingDay.getEmployees().stream().filter(wt -> wt.getId()==1L).findFirst().isPresent());
		assertTrue(startedWorkingDay.getEmployees().stream().filter(wt -> wt.getId()==3L).findFirst().isPresent());
		assertTrue(startedWorkingDay.getEmployees().stream().filter(wt -> wt.getId()==2L).findFirst().isPresent());
		
		
	}
	@Test
	@Order(2)
	void deleteEmployeeFromWorkingDayTest() {
		long waitressId = 2L;
		long workingDayId = 1L;
		WorkingDay day =  workingDayService.deleteWaitressById(waitressId, workingDayId);
		assertEquals(day.getEmployees().size(), 2);
		assertTrue(day.getEmployees().stream().filter(wt -> wt.getId()==3L).findFirst().isPresent());
		assertTrue(day.getEmployees().stream().filter(wt -> wt.getId()==1L).findFirst().isPresent());
		assertFalse(day.getEmployees().stream().filter(wt -> wt.getId()==2L).findFirst().isPresent());
		
	}
	@Test
	@Order(3)
	void deleteAnotherEmployeeFromWorkingDayTest() {
		long waitressId = 1L;
		long workingDayId = 1L;
		WorkingDay day =  workingDayService.deleteWaitressById(waitressId, workingDayId);
		assertEquals(day.getEmployees().size(), 1);
		assertTrue(day.getEmployees().stream().filter(wt -> wt.getId()==3L).findFirst().isPresent());
		assertFalse(day.getEmployees().stream().filter(wt -> wt.getId()==1L).findFirst().isPresent());
		assertFalse(day.getEmployees().stream().filter(wt -> wt.getId()==2L).findFirst().isPresent());
		
	}
	@Test
	@Order(4)
	void deleteLastEmployeeFromWorkingDayTest() {
		long waitressId = 3L;
		long workingDayId = 1L;
		WorkingDay day =  workingDayService.deleteWaitressById(waitressId, workingDayId);
		assertEquals(day.getEmployees().size(), 0);
		assertFalse(day.getEmployees().stream().filter(wt -> wt.getId()==3L).findFirst().isPresent());
		assertFalse(day.getEmployees().stream().filter(wt -> wt.getId()==1L).findFirst().isPresent());
		assertFalse(day.getEmployees().stream().filter(wt -> wt.getId()==2L).findFirst().isPresent());
		
	}
	
	
	
	@Test
	@Order(5)
	void isWorkingDayStartedMethodTest() {
		boolean result = workingDayService.isWorkingDayStarted(workingDayId);
		assertTrue(result);
		
	}
	
	@Test
	@Order(6)
	void updateWorkingDay() {
		WorkingDay day = workingDayService.findWorkingDayById(workingDayId);
		List<Employee> ids = new ArrayList<>();
		ids.add(new Employee(2L));
		ids.add(new Employee(1L));
		day.setEmployees(ids);
		
		WorkingDay updatedDay = workingDayService.updateWorkingDay(day);
		assertEquals(day.getEmployees().size(), 2);
		assertTrue(day.getEmployees().stream().filter(wt -> wt.getId()==2L).findFirst().isPresent());
		assertTrue(day.getEmployees().stream().filter(wt -> wt.getId()==1L).findFirst().isPresent());
		assertEquals(updatedDay.getTotalStartCash().doubleValue(), 10000.00);
		
	}
	
	void closeWorkingDay() {
		
	}
	
	
	
}
