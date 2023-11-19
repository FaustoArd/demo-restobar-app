package com.lord.arbam.services_impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.RestoTableClosed;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.EmployeeRepository;
import com.lord.arbam.repositories.RestoTableClosedRepository;
import com.lord.arbam.repositories.WorkingDayRepository;
import com.lord.arbam.services.WorkingDayService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkingDayServiceImpl implements WorkingDayService {

	private static final Logger log = LoggerFactory.getLogger(WorkingDayServiceImpl.class);

	@Autowired
	private final WorkingDayRepository workingDayRepository;

	@Autowired
	private final RestoTableClosedRepository restoTableClosedRepository;
	
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	private BigDecimal totalResult;

	@Override
	public List<WorkingDay> findAll() {
		return (List<WorkingDay>) workingDayRepository.findAll();
	}

	@Override
	public WorkingDay findWorkingDayById(Long id) {
		return workingDayRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el dia de trabajo"));
	}

	@Override
	public WorkingDay startWorkingDay(WorkingDay workingDay) {

		WorkingDay newWorkingDay = WorkingDay.builder().dayStarted(true).cashierName(workingDay.getCashierName())
				.totalStartCash(workingDay.getTotalStartCash())
				.employees(workingDay.getEmployees()).build();
				
		log.info("Iniciando dia de trabajo");
		return workingDayRepository.save(newWorkingDay);

	}

	@Override
	public WorkingDay updateWorkingDay(WorkingDay workingDay) {
		return workingDayRepository.findById(workingDay.getId()).map(wd -> {
			wd.setTotalStartCash(workingDay.getTotalStartCash());
		
			wd.setEmployees(workingDay.getEmployees());
			log.info("Actualizando working day. WorkingDayServiceImpl.updateWorkingDay");
			return workingDayRepository.save(wd);
		}).orElseThrow(() -> new ItemNotFoundException("No se encontro el dia de trabajo"));
	}

	@Override
	public WorkingDay closeWorkingDay(Long workingDayId) {
		log.info("Finalizando el dia de trabajo. WorkingDayServiceImpl.closeWorkingDay");
		List<RestoTableClosed> tablesClosed = restoTableClosedRepository.findAllByWorkingDayId(workingDayId);
		ListIterator<RestoTableClosed> tablesClosedIt = tablesClosed.listIterator();
		Double totalCashResult = 0.00;
		log.info("Sumando los totales de todas las mesas");
		while (tablesClosedIt.hasNext()) {
			totalCashResult += tablesClosedIt.next().getTotalPrice().doubleValue();
		}
		//BigDecimal totalCashDecimal = new BigDecimal(totalCashResult);
		totalResult = new BigDecimal(totalCashResult);
		log.info("Restando el pago a los empleados");
		return workingDayRepository.findById(workingDayId).map(wDay ->{
			wDay.setTotalCash(totalResult);
		return workingDayRepository.save(wDay);
		}).orElseThrow(()-> new ItemNotFoundException("No se encontro el dia de trabajo"));
		
	}

	@Override
	public WorkingDay deleteWaitressById(Long waitressId, Long workingDayId) {
		log.info("Buscando mesera por id");
		WorkingDay workingDay = findWorkingDayById(workingDayId);
		log.info("Filtrando por id, si es distinto al id eliminado,se agrega a la lista");
		List<Employee> waitresses = workingDay.getEmployees().stream().filter(waitress -> waitress.getId() != waitressId)
				.map(ws -> ws).collect(Collectors.toList());
		workingDay.setEmployees(waitresses);
		log.info("Finalizando eliminacion de mesera");
		return workingDayRepository.save(workingDay);

	}

	@Override
	public boolean isWorkingDayStarted(Long workingDayId) {
		return workingDayRepository.findById(workingDayId).isPresent();
	}

	@Override
	public List<Employee> findCurrentWaitressSelected(Long workingDayId) {
	WorkingDay workingDay=  findWorkingDayById(workingDayId);
	return  employeeRepository.findAllById(workingDay.getEmployees().stream().map(w -> w.getId()).toList());
	}

	

}
