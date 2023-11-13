package com.lord.arbam.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;

public interface RestoTableOrderRepository extends JpaRepository<RestoTableOrder, Long>{
	
	public List<RestoTableOrder> findAllByRestoTableId(Long id);
	
	Optional<RestoTableOrder> findByRestoTableIdAndProductId(Long restoTableId, Long productId);

}
