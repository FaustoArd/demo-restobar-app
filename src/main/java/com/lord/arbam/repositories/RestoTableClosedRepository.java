package com.lord.arbam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.RestoTableClosed;

public interface RestoTableClosedRepository extends JpaRepository<RestoTableClosed, Long>{
	
	public List<RestoTableClosed> findAllByWorkingDayId(Long id);
}
