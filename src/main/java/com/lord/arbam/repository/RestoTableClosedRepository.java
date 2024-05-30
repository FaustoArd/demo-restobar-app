package com.lord.arbam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.WorkingDay;

public interface RestoTableClosedRepository extends JpaRepository<RestoTableClosed, Long>{
	
	public List<RestoTableClosed> findAllByWorkingDayId(Long id);
	
	public List<RestoTableClosed> findAllByWorkingDayIdOrderByTableNumberAsc(Long id);
	
	public void deleteAllByWorkingDay(WorkingDay workingDay);
}
