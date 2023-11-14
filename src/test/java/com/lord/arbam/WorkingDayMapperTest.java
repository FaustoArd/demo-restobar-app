package com.lord.arbam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.lord.arbam.dtos.WorkingDayDto;
import com.lord.arbam.mappers.WorkingDayMapper;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.WorkingDay;

@SpringBootTest
public class WorkingDayMapperTest {

	@Test
	void whenToWorkingDayMethodIsCalled_MustReturnWorkingDayObject() {
		List<Long> employeesId = new ArrayList<>();
		employeesId.add(1L);
		employeesId.add(3L);
		WorkingDayDto workingDayDto = new WorkingDayDto();
		workingDayDto.setCashierName("Leticia");
		workingDayDto.setTotalStartCash(4500.00);
		workingDayDto.setEmployeeId(employeesId);
		WorkingDay workingDay = WorkingDayMapper.INSTANCE.toWorkingDayStart(workingDayDto);
		Optional<Employee> emp1 = workingDay.getWaitresses().stream().filter(emp -> emp.getId() == 1L).findAny();
		Optional<Employee> emp2 = workingDay.getWaitresses().stream().filter(emp -> emp.getId() == 3L).findAny();
		assertEquals(workingDay.getCashierName(), "Leticia");
		assertEquals(workingDay.getTotalStartCash().doubleValue(), 4500.00);
		assertEquals(workingDay.getWaitresses().stream().map(emp -> emp.getId()).count(), 2);
		assertEquals(emp1.get().getId().longValue(), 1L);
		assertEquals(emp2.get().getId().longValue(), 3L);

	}

}
