package com.lord.arbam.services_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.EmployeeNotSelectedException;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.RestoTableClosed;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.WorkingDayRepository;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.RestoTableClosedService;
import com.lord.arbam.services.WorkingDayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkingDayServiceImpl implements WorkingDayService {

	private static final Logger log = LoggerFactory.getLogger(WorkingDayServiceImpl.class);

	@Autowired
	private final WorkingDayRepository workingDayRepository;

	@Autowired
	private final EmployeeService employeeService;

	@Autowired
	private final RestoTableClosedService restoTableClosedService;

	@Override
	public List<WorkingDay> findAll() {
		return (List<WorkingDay>) workingDayRepository.findAll();
	}

	@Override
	public WorkingDay findWorkingDayById(Long id) {
		return workingDayRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el dia de trabajo"));
	}

	@Override
	public WorkingDay startWorkingDay(WorkingDay workingDay){
		log.info("Starting working day. WorkingDayServiceImpl.startWorkingDay");
		List<Employee> waitresses = employeeService
				.findAllById(workingDay.getWaitresses().stream().map(w -> w.getId()).toList());
		WorkingDay newWorkingDay = WorkingDay.builder().cashierName(workingDay.getCashierName())
				.totalStartCash(workingDay.getTotalStartCash())
				.totalPostEmployeeSalary(workingDay.getTotalPostEmployeeSalary()).waitresses(waitresses).dayStarted(true).build();
		return workingDayRepository.save(newWorkingDay);

	}

	@Override
	public WorkingDay updateWorkingDay(WorkingDay workingDay) {
		log.info("Updating working day. WorkingDayServiceImpl.updateWorkingDay");
		List<Employee> waitresses = employeeService
				.findAllById(workingDay.getWaitresses().stream().map(w -> w.getId()).toList());
		WorkingDay updatedWorkingDay = WorkingDay.builder().id(workingDay.getId())
				.cashierName(workingDay.getCashierName()).totalStartCash(workingDay.getTotalStartCash())
				.totalPostEmployeeSalary(workingDay.getTotalPostEmployeeSalary()).waitresses(waitresses).build();
		return workingDayRepository.save(updatedWorkingDay);
	}

	@Override
	public WorkingDay closeWorkingDay(Long workingDayId) {
		log.info("Closing working day. WorkingDayServiceImpl.closeWorkingDay");
		List<RestoTableClosed> tablesClosed = restoTableClosedService.findAllByWorkingDayId(workingDayId);

		ListIterator<RestoTableClosed> tablesClosedIt = tablesClosed.listIterator();
		Double totalCashResult = 0.00;
		while (tablesClosedIt.hasNext()) {
			totalCashResult += tablesClosedIt.next().getTotalPrice().doubleValue();
		}
		System.out.println(totalCashResult);
		BigDecimal totalCashDecimal = new BigDecimal(totalCashResult);
		return workingDayRepository.findById(workingDayId).map(m -> {
			m.setId(workingDayId);
			m.setTotalCash(totalCashDecimal);
			return workingDayRepository.save(m);
		}).orElseThrow(() -> new ItemNotFoundException("No se encontro el dia de trabajo"));

	}

}
