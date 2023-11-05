package com.lord.arbam.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.RestoTableOrder;

public interface RestoTableOrderRepository extends JpaRepository<RestoTableOrder, Long>{
	
	Optional<List<RestoTableOrder>> findByRestoTablesId(Long id);

}
