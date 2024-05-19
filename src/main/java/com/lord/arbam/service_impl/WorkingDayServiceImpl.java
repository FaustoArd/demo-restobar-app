package com.lord.arbam.service_impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.RestoTableOpenException;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.repository.EmployeeRepository;
import com.lord.arbam.repository.RestoTableClosedRepository;
import com.lord.arbam.repository.WorkingDayRepository;
import com.lord.arbam.service.WorkingDayService;

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
		Calendar date = Calendar.getInstance();
		WorkingDay newWorkingDay = WorkingDay.builder().dayStarted(true).date(date)
				.totalStartCash(workingDay.getTotalStartCash()).employees(workingDay.getEmployees()).build();

		log.info("Iniciando dia de trabajo");
		return workingDayRepository.save(newWorkingDay);

	}

	@Override
	public WorkingDay updateWorkingDay(WorkingDay workingDay) {
		log.info("Actualizando working day.");
		return workingDayRepository.findById(workingDay.getId()).map(wd -> {
			wd.setTotalStartCash(workingDay.getTotalStartCash());
			wd.setEmployees(workingDay.getEmployees());
			return workingDayRepository.save(wd);
		}).orElseThrow(() -> new ItemNotFoundException("Working Day not found"));
	}

	@Override
	public WorkingDay closeWorkingDay(Long workingDayId) {
		log.info("Sumando los totales de las mesas.");
		double totalTablesResult = restoTableClosedRepository.findAllByWorkingDayId(workingDayId).stream()
				.mapToDouble(res -> res.getTotalPrice().doubleValue()).sum();
		BigDecimal bdTotalTablesResult = new BigDecimal(totalTablesResult); 
		log.info("Filtrando totales por metodo de pago");
		double totalCashResult = restoTableClosedRepository.findAllByWorkingDayId(workingDayId).stream()
				.filter(res -> res.getPaymentMethod().equals("Efectivo")).mapToDouble(ef -> ef.getTotalPrice().doubleValue()).sum();
		double totalDebitResult = restoTableClosedRepository.findAllByWorkingDayId(workingDayId).stream()
				.filter(res -> res.getPaymentMethod().equals("Tarjeta de debito")).mapToDouble(ef -> ef.getTotalPrice().doubleValue()).sum();
		double totalTransResult = restoTableClosedRepository.findAllByWorkingDayId(workingDayId).stream()
				.filter(res -> res.getPaymentMethod().equals("Transferencia")).mapToDouble(ef -> ef.getTotalPrice().doubleValue()).sum();
		double totalCreditResult = restoTableClosedRepository.findAllByWorkingDayId(workingDayId).stream()
				.filter(res -> res.getPaymentMethod().equals("Tarjeta de credito")).mapToDouble(ef -> ef.getTotalPrice().doubleValue()).sum();
		double totalMpResult = restoTableClosedRepository.findAllByWorkingDayId(workingDayId).stream()
				.filter(res -> res.getPaymentMethod().equals("Mercado pago")).mapToDouble(ef -> ef.getTotalPrice().doubleValue()).sum();
		
		log.info("Guardando total pago a empleados y todos los totales");
		return workingDayRepository.findById(workingDayId).map(wDay -> {
			double employeeTotalResult = wDay.getEmployees().stream()
					.mapToDouble(emp -> emp.getEmployeeJob().getEmployeeSalary().doubleValue()).sum();
			wDay.setTotalEmployeeSalary(new BigDecimal(employeeTotalResult));
			wDay.setTotalCash(new BigDecimal(totalCashResult));
			wDay.setTotalDebit(new BigDecimal(totalDebitResult));
			wDay.setTotalTransf(new BigDecimal(totalTransResult));
			wDay.setTotalCredit(new BigDecimal(totalCreditResult));
			wDay.setTotalMP(new BigDecimal(totalMpResult));
			wDay.setTotalWorkingDay(bdTotalTablesResult.add(wDay.getTotalStartCash()));
			wDay.setTotalWorkingDayWithDiscount(wDay.getTotalWorkingDay().subtract(wDay.getTotalEmployeeSalary()));
			wDay.setDayStarted(false);
			return workingDayRepository.save(wDay);

		}).orElseThrow(() -> new ItemNotFoundException("Working Day not found"));
	}

	@Override
	public WorkingDay deleteEmployeesById(Long employeeId, Long workingDayId) {
		log.info("Buscando empleado por id");
		WorkingDay workingDay = findWorkingDayById(workingDayId);
		List<Employee> employees = workingDay.getEmployees().stream()
				.filter(e -> e.getId() != employeeId).map(es -> es).collect(Collectors.toList());
		workingDay.setEmployees(employees);
		log.info("Finalizando eliminacion de empleado de la jornada de trabajo");
		return workingDayRepository.save(workingDay);

	}

	@Override
	public boolean isWorkingDayStarted(Long workingDayId) {
		return workingDayRepository.findById(workingDayId).isPresent();
	}

	@Override
	public List<Employee> findCurrentEmployeesSelected(Long workingDayId) {
		WorkingDay workingDay = findWorkingDayById(workingDayId);
		return employeeRepository.findAllById(workingDay.getEmployees().stream().map(w -> w.getId()).toList());
	}

	@Override
	public List<WorkingDay> findAllByOrderByDateAsc() {
	return (List<WorkingDay>)workingDayRepository.findAllByOrderByDateAsc();
	}

	@Override
	public void deleteWorkingDayById(Long id) {
		log.info("Eliminado jornada de trbajo por id");
		if(workingDayRepository.existsById(id)) {
		WorkingDay wdToBeDeleted   = findWorkingDayById(id);
		if(wdToBeDeleted.isDayStarted()) {
			throw new RestoTableOpenException("No se puede eliminar, el dia de trabajo sigue abierto.");
		
		}else {
			log.info("Jornada de trabajo cerrada, se puede eliminar");
			workingDayRepository.deleteById(id);
		}
		
		}else {
			throw new ItemNotFoundException("Working day not found");
		}
	}

	@Override
	public List<WorkingDay> findByEmployeesId(Long employeeId) {
	return (List<WorkingDay>)workingDayRepository.findByEmployeesId(employeeId);
	
	}
	

}
