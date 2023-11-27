package com.lord.arbam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lord.arbam.model.WorkingDay;

public interface WorkingDayRepository extends JpaRepository<WorkingDay, Long>{
	
	public List<WorkingDay> findAllByOrderByDateAsc();
	


}
