package com.lord.arbam.services_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.WorkingDayRepository;
import com.lord.arbam.services.WorkingDayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkingDayServiceImpl implements WorkingDayService {
	
	@Autowired
	private final WorkingDayRepository workingDayRepository;

	@Override
	public List<WorkingDay> findAll() {
		return (List<WorkingDay>)workingDayRepository.findAll();
	}

	@Override
	public WorkingDay findWorkingDayById(Long id) {
	return workingDayRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("No se encontro el dia de trabajo"));
	}
	
	@Override
	public WorkingDay startWorkingDay(WorkingDay workingDay) {
		
		/*WorkingDay newWorkingDay = WorkingDay.builder()
				.totalStartCash(workingDay.getTotalStartCash())
				.*/
		return null;
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
