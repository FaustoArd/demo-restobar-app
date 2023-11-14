package com.lord.arbam.services_impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.WorkingDayRepository;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.WorkingDayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkingDayServiceImpl implements WorkingDayService {

	@Autowired
	private final WorkingDayRepository workingDayRepository;

	private final EmployeeService employeeService;

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
	public WorkingDay startWorkingDay(WorkingDay workingDay) {
		List<Employee> waitresses = employeeService
				.findAllById(workingDay.getWaitresses().stream().map(w -> w.getId()).toList());
		WorkingDay newWorkingDay = WorkingDay.builder().cashierName(workingDay.getCashierName())
				.totalStartCash(workingDay.getTotalStartCash()).waitresses(waitresses).build();
		return workingDayRepository.save(newWorkingDay);

	}

	@Override
	public WorkingDay updateWorkingDay(WorkingDay workingDay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkingDay closeWorkingDay(WorkingDay workingDay) {
		// TODO Auto-generated method stub
		return null;
	}

}
