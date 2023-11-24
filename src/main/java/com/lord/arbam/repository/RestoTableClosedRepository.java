package com.lord.arbam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.RestoTableClosed;

public interface RestoTableClosedRepository extends JpaRepository<RestoTableClosed, Long>{
	
	public List<RestoTableClosed> findAllByWorkingDayId(Long id);
}
