package com.lord.arbam.service_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.RestoTableOpenException;
import com.lord.arbam.exception.ValueAlreadyExistException;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrder;
import com.lord.arbam.model.RestoTableOrderClosed;
import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.repository.EmployeeRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.RestoTableClosedRepository;
import com.lord.arbam.repository.RestoTableOrderClosedRepository;
import com.lord.arbam.repository.RestoTableOrderRepository;
import com.lord.arbam.repository.RestoTableRepository;
import com.lord.arbam.repository.WorkingDayRepository;
import com.lord.arbam.service.RestoTableService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestoTableServiceImpl implements RestoTableService {
	
	private static final Logger log = LoggerFactory.getLogger(RestoTableServiceImpl.class);
	
	@Autowired
	private final RestoTableRepository restoTableRepository;
	
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	private final RestoTableClosedRepository restoTableClosedRepository;
	
	@Autowired
	private final RestoTableOrderClosedRepository restoTableOrderClosedRepository;
	
	@Autowired
	private final WorkingDayRepository workingDayRepository;
	
	@Autowired
	private final PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private final RestoTableOrderRepository restoTableOrderRepository;
	
	@Override
	public RestoTable findRestoTableById(Long id) {
		return restoTableRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Table not found. RestoTableServiceImpl.findRestoTableById"));
	}

	@Override
	public List<RestoTable> findAllRestoTables() {
		return (List<RestoTable>)restoTableRepository.findAll();
	}

	@Override
	public RestoTable openRestoTable(RestoTable restoTable) {
		Optional<RestoTable> existingTableNumber = findByTableNumber(restoTable.getTableNumber());
		if(existingTableNumber.isPresent()) {
			log.warn("Cant open table, Table number already exist");
			throw new ValueAlreadyExistException("Table number already exist");
		}else {
		RestoTable newRestotable = findRestoTableById(restoTable.getId());
		Employee employee = employeeRepository.findById(restoTable.getEmployee().getId()).orElseThrow(()-> new ItemNotFoundException("Employee not found"));
		newRestotable.setEmployee(employee);
		newRestotable.setTableNumber(restoTable.getTableNumber());
		newRestotable.setOpen(true);
		newRestotable.setTotalTablePrice(new BigDecimal(0));
		log.info("Open table");
		return restoTableRepository.save(newRestotable);
		}
	}
	
	@Override
	public RestoTable updateRestoTableTotalPrice(RestoTable restoTable, List<RestoTableOrder> orders) {
		log.info("Finding orders total price");
		ListIterator<RestoTableOrder> ordersIt = orders.listIterator();
		Double updatedPrice = 0.00;
		log.info("Adding orders total price");
		while(ordersIt.hasNext()) {
			updatedPrice += ordersIt.next().getTotalOrderPrice().doubleValue();
		}
		log.info("Updating resto table total price");
		restoTable.setTotalTablePrice(new BigDecimal(updatedPrice));
		return restoTableRepository.save(restoTable);
	}
	

	@Transactional
	@Override
	public RestoTable closeRestoTable(Long restoTableId,Long workingDayId,PaymentMethod paymentMethod) {
		log.info("Starting close resto table id: " + restoTableId);
	RestoTable findedTable = restoTableRepository.findById(restoTableId).orElseThrow(()-> new ItemNotFoundException("resto table not found"));
		Employee employee = employeeRepository.findById(findedTable.getEmployee().getId()).orElseThrow(()-> new ItemNotFoundException("No se encontro el empleado"));
		WorkingDay workingDay = workingDayRepository.findById(workingDayId).orElseThrow(()-> new ItemNotFoundException("No se encontro el dia de trabajo"));
		log.info("Copying table, id: " + restoTableId + " data");
		RestoTableClosed tableClosed = RestoTableClosed.builder()
				.tableNumber(findedTable.getTableNumber())
				.employeeName(employee.getEmployeeName())
				.totalPrice(findedTable.getTotalTablePrice())
				.paymentMethod(paymentMethod.getPaymentMethod())
				.workingDay(workingDay).build();
		
		RestoTableClosed savedTableClosed =  restoTableClosedRepository.save(tableClosed);
		log.info("Restarting resto table, id: " + restoTableId);
		findedTable.setEmployee(null);
		findedTable.setTableNumber(null);
		findedTable.setOpen(false);
		findedTable.setPaymentMethod(null);
		findedTable.setTotalTablePrice(null);
		List<RestoTableOrder> orders = restoTableOrderRepository.findAllByRestoTableId(restoTableId);
		List<RestoTableOrderClosed> ordersClosed = mapTableOrderToTableOrderClosed(orders, savedTableClosed);
		log.info("Saving orders  table backup. ");
		restoTableOrderClosedRepository.saveAll(ordersClosed);
		log.info("Deleting orders from Table id: " + restoTableId);
		restoTableOrderRepository.deleteAll(orders);
		return restoTableRepository.save(findedTable);
		
	
	}
	
	private static List<RestoTableOrderClosed> mapTableOrderToTableOrderClosed(List<RestoTableOrder> orders,RestoTableClosed tableClosed) {
	List<RestoTableOrderClosed> ordersClosed = orders.stream().map(orderItem -> {
		RestoTableOrderClosed orderClosedItem = new RestoTableOrderClosed();
		orderClosedItem.setProductName(orderItem.getProduct().getProductName());
		orderClosedItem.setProductQuantity(orderItem.getProductQuantity());
		orderClosedItem.setTotalOrderPrice(orderItem.getTotalOrderPrice());
		orderClosedItem.setRestoTableClosed(tableClosed);
		return orderClosedItem;
	}).toList();
	return ordersClosed;
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
	
	@Override
	public List<PaymentMethod> findAllPaymentMethods(){
		return (List<PaymentMethod>)paymentMethodRepository.findAll();
	}

	@Override
	public void checkTablesOpen() {
		 if(findAllRestoTables().stream().filter(r -> r.isOpen()).findFirst().isPresent()) {
			 throw new RestoTableOpenException("Cannot close Working Day because they are One or More tables Open");
		 }
		
	}

	

}
