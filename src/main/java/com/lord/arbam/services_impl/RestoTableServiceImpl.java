package com.lord.arbam.services_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.exceptions.ValueAlreadyExistException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.PaymentMethod;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableClosed;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.EmployeeRepository;
import com.lord.arbam.repositories.PaymentMethodRepository;
import com.lord.arbam.repositories.RestoTableClosedRepository;
import com.lord.arbam.repositories.RestoTableRepository;
import com.lord.arbam.repositories.WorkingDayRepository;
import com.lord.arbam.services.RestoTableService;

import jakarta.persistence.PersistenceContext;
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
	private final WorkingDayRepository workingDayRepository;
	
	@Autowired
	private final PaymentMethodRepository paymentMethodRepository;
	
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
			log.error("No se puede abrir la mesa, el numero ingresado ya existe");
			throw new ValueAlreadyExistException("El numero de mesa ya existe");
		}else {
		RestoTable newRestotable = findRestoTableById(restoTable.getId());
		Employee employee = employeeRepository.findById(restoTable.getEmployee().getId()).orElseThrow(()-> new ItemNotFoundException("No se encontro el empleado"));
		newRestotable.setEmployee(employee);
		newRestotable.setTableNumber(restoTable.getTableNumber());
		newRestotable.setOpen(true);
		newRestotable.setTotalTablePrice(new BigDecimal(0));
		log.info("Abriendo mesa");
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
		log.info("Actualizando total de la mesa");
		return restoTableRepository.save(restoTable);
	}
	

	@Transactional
	@Override
	public RestoTable closeRestoTable(Long restoTableId,Long workingDayId,String paymentMethod) {
		log.info("Iniciando el cierre de mesa id:");
	RestoTable findedTable = restoTableRepository.findById(restoTableId).orElseThrow(()-> new ItemNotFoundException("No se encontro la mesa"));
		Employee employee = employeeRepository.findById(findedTable.getEmployee().getId()).orElseThrow(()-> new ItemNotFoundException("No se encontro el empleado"));
		WorkingDay workingDay = workingDayRepository.findById(workingDayId).orElseThrow(()-> new ItemNotFoundException("No se encontro el dia de trabajo"));
		RestoTableClosed tableClosed = RestoTableClosed.builder()
				.tableNumber(findedTable.getTableNumber())
				.employeeName(employee.getEmployeeName())
				.totalPrice(findedTable.getTotalTablePrice())
				.paymentMethod(paymentMethod)
				.workingDay(workingDay).build();
		log.info("Generando backup de mesa");
		restoTableClosedRepository.save(tableClosed);
		findedTable.setEmployee(null);
		findedTable.setTableNumber(null);
		findedTable.setOpen(false);
		findedTable.setPaymentMethod(null);
		findedTable.setTotalTablePrice(null);
		log.info("Reiniciando la mesa");
		return restoTableRepository.save(findedTable);
	
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

	

}
