package com.lord.arbam;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;

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
import com.lord.arbam.mappers.WorkingDayMapper;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.services.WorkingDayService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class WorkingDayServiceTest {
	
	@Autowired
	private WorkingDayService workingDayService;
	
	@Test
	@Order(1)
	void WhenStartWorkingDayMethodIsCalled_MustReturnWorkingDayObject() {
		List<Long> empsId = new ArrayList<>();
		empsId.add(1L);
		empsId.add(3L);
		WorkingDayDto workingDayDto = new WorkingDayDto();
		workingDayDto.setCashierName("Miguel");
		workingDayDto.setTotalStartCash(4500.00);
		workingDayDto.setEmployeeId(empsId);
		WorkingDay day = WorkingDayMapper.INSTANCE.toWorkingDayStart(workingDayDto);
		WorkingDay startedWorkingDay = workingDayService.startWorkingDay(day);
		assertTrue(startedWorkingDay.getId()!=null);
		assertEquals(startedWorkingDay.getCashierName(),"Miguel");
		assertEquals(startedWorkingDay.getTotalStartCash().doubleValue(), 4500.00);
		assertEquals(startedWorkingDay.getWaitresses().get(0).getEmployeeName(), "Carla");
		assertEquals(startedWorkingDay.getWaitresses().get(1).getEmployeeName(), "Silvana");
	}
	//@Test
	//@Order(2)
	void WhenStartWorkingDayMethodIsCalled_WithoutSelectingWaitresses_MustReturnWorkingDayObject() {
		
		WorkingDayDto workingDayDto = new WorkingDayDto();
		workingDayDto.setCashierName("Miguel");
		workingDayDto.setTotalStartCash(4500.00);
		WorkingDay day = WorkingDayMapper.INSTANCE.toWorkingDayStart(workingDayDto);
		WorkingDay startedWorkingDay = workingDayService.startWorkingDay(day);
		assertTrue(startedWorkingDay.getId()!=null);
		assertEquals(startedWorkingDay.getCashierName(),"Miguel");
		assertEquals(startedWorkingDay.getTotalStartCash().doubleValue(), 4500.00);
		
	}

}
