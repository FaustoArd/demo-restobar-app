package com.lord.arbam.services_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.exceptions.ValueAlreadyExistException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.repositories.RestoTableRepository;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.RestoTableService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestoTableServiceImpl implements RestoTableService {
	
	@Autowired
	private final RestoTableRepository restoTableRepository;
	
	@Autowired
	private final EmployeeService employeeService;
	
	@Override
	public RestoTable findRestoTableById(Long id) {
		return restoTableRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No se encontro la mesa. RestoTableServiceImpl.findRestoTableById"));
	}

	@Override
	public List<RestoTable> findAllRestoTables() {
		return (List<RestoTable>)restoTableRepository.findAll();
	}

	@Override
	public RestoTable openRestoTable(RestoTable restoTable) {
		Optional<RestoTable> existingTableNumber = findByTableNumber(restoTable.getTableNumber());
		if(existingTableNumber.isPresent()) {
			throw new ValueAlreadyExistException("El numero de mesa ya existe");
		}else {
		RestoTable newRestotable = findRestoTableById(restoTable.getId());
		Employee employee = employeeService.findEmployeeById(restoTable.getEmployee().getId());
		newRestotable.setEmployee(employee);
		newRestotable.setTableNumber(restoTable.getTableNumber());
		newRestotable.setOpen(true);
		newRestotable.setTotalTablePrice(new BigDecimal(0));
		return restoTableRepository.save(newRestotable);
		}
	}
	
	@Override
	public RestoTable updateRestoTableTotalPrice(RestoTable restoTable, List<RestoTableOrder> orders) {
		ListIterator<RestoTableOrder> ordersIt = orders.listIterator();
		Double updatedPrice = 0.00;
		while(ordersIt.hasNext()) {
			updatedPrice += ordersIt.next().getTotalOrderPrice().doubleValue();
		}
		restoTable.setTotalTablePrice(new BigDecimal(updatedPrice));
		return restoTableRepository.save(restoTable);
	}
	

	@Override
	public RestoTable closeRestoTable(RestoTable restoTable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RestoTable> findAllByOrderByIdAsc() {
		return(List<RestoTable>)restoTableRepository.findAllByOrderByIdAsc();
	}

	@Override
	public RestoTable saveRestoTable(RestoTable restoTable) {
		return restoTableRepository.save(restoTable);
	}

	@Override
	public Optional<RestoTable> findByTableNumber(Integer tableNumber) {
		return restoTableRepository.findByTableNumber(tableNumber);
	}

	

}
