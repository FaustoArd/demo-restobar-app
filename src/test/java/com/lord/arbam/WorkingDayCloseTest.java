package com.lord.arbam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.services.WorkingDayService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class WorkingDayCloseTest {
	
	@Autowired
	private WorkingDayService workingDayService;
	
	private Long workingDayId;

	
	@Test
	@Order(1)
	public void setup() {
		WorkingDay workingDay = WorkingDay.builder().cashierName("Mirta").totalStartCash(new BigDecimal(5000.00))
			.build();
				WorkingDay startedWorkingDay = workingDayService.startWorkingDay(workingDay);
				this.workingDayId = startedWorkingDay.getId();
				assertTrue(startedWorkingDay.isDayStarted());
				assertEquals(startedWorkingDay.getCashierName(), "Mirta");
				assertEquals(startedWorkingDay.getTotalStartCash().doubleValue(), 5000.00);
			
	}
	
	@Test
	@Order(2)
	public void closeWorkingDayTest() {
		WorkingDay workingDay = workingDayService.closeWorkingDay(workingDayId);
		
		
	}
}
