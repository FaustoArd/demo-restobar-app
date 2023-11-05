package com.lord.arbam.services_impl;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.repositories.RestoTableRepository;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.RestoTableOrderService;
import com.lord.arbam.services.RestoTableService;

@Service
public class RestoTableServiceImpl implements RestoTableService {
	
	@Autowired
	private final RestoTableRepository restoTableRepository;
	
	@Autowired
	private final RestoTableOrderService restoTableOrderService;
	
	@Autowired
	private final EmployeeService employeeService;
	
	public RestoTableServiceImpl(RestoTableRepository restoTableRepository,RestoTableOrderService restoTableOrderService,EmployeeService employeeService) {
		this.restoTableRepository = restoTableRepository;
		this.restoTableOrderService = restoTableOrderService;
		this.employeeService = employeeService;
	}

	@Override
	public RestoTable findRestoTableById(Long id) {
		return restoTableRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No se encontro la mesa. RestoTableServiceImpl.findRestoTableById"));
	}

	@Override
	public List<RestoTable> findAllRestoTables() {
		return (List<RestoTable>)restoTableRepository.findAll();
	}

	@Override
	public RestoTable createRestoTable(RestoTable restoTable) {
		Employee employee = employeeService.findEmployeeById(restoTable.getEmployee().getId());
		restoTable.setEmployee(employee);
		restoTable.setOpen(true);
		return restoTableRepository.save(restoTable);
	}

	@Override
	public RestoTable addOrderToRestotable(RestoTable restoTable) {
		RestoTableOrder order = restoTableOrderService.findOrderById(restoTable.getTableOrder().getId());
		restoTable.setTableOrder(order);
		return restoTableRepository.save(restoTable);
	}

	@Override
	public RestoTable updateRestoTablePrice(RestoTable restoTable) {
		Double totalTablePrice = 0.00;
		List<RestoTableOrder> orders = restoTableOrderService.findByRestoTablesId(restoTable.getId());
		ListIterator<RestoTableOrder> ordersIt = orders.listIterator();
		while(ordersIt.hasNext()) {
			totalTablePrice += ordersIt.next().getTotalOrderPrice();
		}
		restoTable.setTotalTablePrice(totalTablePrice);
		return restoTableRepository.save(restoTable);
	}

	@Override
	public RestoTable closeRestoTable(RestoTable restoTable) {
		// TODO Auto-generated method stub
		return null;
	}

}
