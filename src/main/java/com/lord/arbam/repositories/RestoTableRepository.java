package com.lord.arbam.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lord.arbam.models.RestoTable;

public interface RestoTableRepository extends JpaRepository<RestoTable, Long> {
	
 public List<RestoTable> findAllByOrderByIdAsc();
 
 public Optional<RestoTable> findByTableNumber(Integer tableNumber);

}
