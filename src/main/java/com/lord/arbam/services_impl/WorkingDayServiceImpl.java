package com.lord.arbam.services_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.RestoTableClosed;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.RestoTableClosedRepository;
import com.lord.arbam.repositories.WorkingDayRepository;
import com.lord.arbam.services.EmployeeService;

import com.lord.arbam.services.WorkingDayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkingDayServiceImpl implements WorkingDayService {

	private static final Logger log = LoggerFactory.getLogger(WorkingDayServiceImpl.class);

	@Autowired
	private final WorkingDayRepository workingDayRepository;

	@Autowired
	private final RestoTableClosedRepository restoTableClosedRepository;

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
	public Long initWorkingDay() {
		WorkingDay day = WorkingDay.builder().dayStarted(true).build();
		WorkingDay newWorkingDay =  workingDayRepository.save(day);
		return newWorkingDay.getId();
	}

	@Override
	public WorkingDay startWorkingDay(WorkingDay workingDay){
		
		WorkingDay newWorkingDay= WorkingDay.builder().dayStarted(true).cashierName(workingDay.getCashierName())
				.totalStartCash(workingDay.getTotalStartCash()).waitresses(workingDay.getWaitresses())
				.totalPostEmployeeSalary(workingDay.getTotalPostEmployeeSalary())
				.totalCashierSalary(workingDay.getTotalCashierSalary()).build();
		return workingDayRepository.save(newWorkingDay);
	
		
	

	}

	@Override
	public WorkingDay updateWorkingDay(WorkingDay workingDay) {
		log.info("Updating working day. WorkingDayServiceImpl.updateWorkingDay");
		return workingDayRepository.findById(workingDay.getId()).map(wd -> {
			wd.setTotalStartCash(workingDay.getTotalStartCash());
			wd.setCashierName(workingDay.getCashierName());
			wd.setTotalPostEmployeeSalary(workingDay.getTotalPostEmployeeSalary());
			wd.setWaitresses(workingDay.getWaitresses());
			return workingDayRepository.save(wd);
		}).orElseThrow(()-> new ItemNotFoundException("No se encontro el dia de trabajo"));
	}

	@Override
	public WorkingDay closeWorkingDay(Long workingDayId) {
		log.info("Finalizando el dea de trabajo. WorkingDayServiceImpl.closeWorkingDay");
		List<RestoTableClosed> tablesClosed = restoTableClosedRepository.findAllByWorkingDayId(workingDayId);
		ListIterator<RestoTableClosed> tablesClosedIt = tablesClosed.listIterator();
		Double totalCashResult = 0.00;
		while (tablesClosedIt.hasNext()) {
			totalCashResult += tablesClosedIt.next().getTotalPrice().doubleValue();
		}
		System.out.println(totalCashResult);
		BigDecimal totalCashDecimal = new BigDecimal(totalCashResult);
		return workingDayRepository.findById(workingDayId).map(wd -> {
			wd.setId(workingDayId);
			wd.setTotalCash(totalCashDecimal);
			return workingDayRepository.save(wd);
		}).orElseThrow(() -> new ItemNotFoundException("No se encontro el dia de trabajo"));

	}

	@Override
	public WorkingDay deleteWaitressById(Long waitressId,Long workingDayId) {
		WorkingDay workingDay = findWorkingDayById(workingDayId);
		 List<Employee> emps =  workingDay.getWaitresses().stream().filter(waitress -> waitress.getId()!=waitressId).map(ws -> ws).collect(Collectors.toList());
		 workingDay.setWaitresses(emps);
		return workingDayRepository.save(workingDay);
		
	}

	@Override
	public boolean isWorkingDayStarted(Long workingDayId) {
		return workingDayRepository.findById(workingDayId).isPresent();
	}

	

}
