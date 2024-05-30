package com.lord.arbam.service_impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lord.arbam.dto.WorkingDayPaymentTableDto;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.RestoTableOpenException;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.OrderPaymentMethod;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrderClosed;
import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.repository.EmployeeRepository;
import com.lord.arbam.repository.OrderPaymentMethodRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.RestoTableClosedRepository;
import com.lord.arbam.repository.RestoTableOrderClosedRepository;
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
	private final PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private final OrderPaymentMethodRepository orderPaymentMethodRepository;

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

	

	private static double getTablesOrderTotals(List<RestoTableOrderClosed> orders) {
		return orders.stream().mapToDouble(order -> order.getTotalOrderPrice().doubleValue()).sum();
	}

	@Override
	public WorkingDay closeWorkingDay(Long workingDayId) {
		log.info("Sumando los totales de las mesas.");
		List<WorkingDayPaymentTableDto> totals = restoTableClosedRepository.findAllByWorkingDayId(workingDayId).stream()
				.map(table -> {
					return getTotalResults(orderPaymentMethodRepository.findAllByRestoTableClosed(table));
				}).toList();
		log.info("Guardando todos los totales");
		WorkingDay workingDay = workingDayRepository.findById(workingDayId).map(wDay -> {
			double employeeTotalResult = wDay.getEmployees().stream()
					.mapToDouble(emp -> emp.getEmployeeJob().getEmployeeSalary().doubleValue()).sum();

			wDay.setTotalEmployeeSalary(new BigDecimal(employeeTotalResult));
			wDay.setTotalCash(getTableTotalCash(totals));
			wDay.setTotalDebit(getTableTotalDebit(totals));
			wDay.setTotalTransf(getTableTotalTransf(totals));
			wDay.setTotalCredit(getTableTotalCredit(totals));
			wDay.setTotalMP(getTableTotalMp(totals));
			wDay.setTotalWorkingDay(getTotalWorkingDay(totals).add(wDay.getTotalStartCash()));
			wDay.setTotalWorkingDayWithDiscount(wDay.getTotalWorkingDay().subtract(wDay.getTotalEmployeeSalary()));
			wDay.setDayStarted(false);
			return workingDayRepository.save(wDay);

		}).orElseThrow(() -> new ItemNotFoundException("No se encontro el dia de trabajo"));
		System.out.println("cash:" + workingDay.getTotalCash());
		System.out.println("debit: " + workingDay.getTotalDebit());
		System.out.println("transf: " + workingDay.getTotalTransf());
		System.out.println("mp: " + workingDay.getTotalMP());
		return workingDay;
	}
	private WorkingDayPaymentTableDto getTotalResults(List<OrderPaymentMethod> orderPaymentMethods) {

		WorkingDayPaymentTableDto wdPtDto = setWDPTDtoToDefault();
		
		orderPaymentMethods.stream().forEach(payment -> {
			if (payment.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("efectivo")) {
				wdPtDto.setTotalCash(wdPtDto.getTotalCash().add(payment.getOrders().stream().map(order -> order.getTotalOrderPrice())
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
			}
			if (payment.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("tarjeta de debito")) {
				
				wdPtDto.setTotalDebit(wdPtDto.getTotalDebit().add(payment.getOrders().stream().map(order -> order.getTotalOrderPrice())
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
			}
			if (payment.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("transferencia")) {
			
				wdPtDto.setTotalTransf(wdPtDto.getTotalTransf().add(payment.getOrders().stream().map(order -> order.getTotalOrderPrice())
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
			}
			if (payment.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("tarjeta de credito")) {
			
				wdPtDto.setTotalCredit(wdPtDto.getTotalCredit().add(payment.getOrders().stream().map(order -> order.getTotalOrderPrice())
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
			}
			if (payment.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("mercado pago")) {
				
				wdPtDto.setTotalMP(wdPtDto.getTotalMP().add(payment.getOrders().stream().map(order -> order.getTotalOrderPrice())
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
			}
			wdPtDto.setTotalWorkingDay(
					wdPtDto.getTotalWorkingDay().add(new BigDecimal(getTablesOrderTotals(payment.getOrders()))));
		});
		return wdPtDto;
	}
	
	private static  WorkingDayPaymentTableDto setWDPTDtoToDefault() {
		WorkingDayPaymentTableDto wDPTDto = new WorkingDayPaymentTableDto();
		wDPTDto.setTotalWorkingDay(new BigDecimal(0));
		wDPTDto.setTotalCash(new BigDecimal(0));
		wDPTDto.setTotalCredit(new BigDecimal(0));
		wDPTDto.setTotalDebit(new BigDecimal(0));
		wDPTDto.setTotalEmployeeSalary(new BigDecimal(0));
		wDPTDto.setTotalMP(new BigDecimal(0));
		wDPTDto.setTotalStartCash(new BigDecimal(0));
		wDPTDto.setTotalTransf(new BigDecimal(0));
		wDPTDto.setTotalWorkingDay(new BigDecimal(0));
		wDPTDto.setTotalWorkingDayWithDiscount(new BigDecimal(0));
		return wDPTDto;
		
	}

	private static BigDecimal getTableTotalCash(List<WorkingDayPaymentTableDto> totals) {

		return totals.stream().filter(f -> f.getTotalCash() != null).map(tableTotal -> tableTotal.getTotalCash())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getTableTotalDebit(List<WorkingDayPaymentTableDto> totals) {
		return totals.stream().filter(f -> f.getTotalDebit() != null).map(tableTotal -> tableTotal.getTotalDebit())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

	}

	private static BigDecimal getTableTotalTransf(List<WorkingDayPaymentTableDto> totals) {
		return totals.stream().filter(f -> f.getTotalTransf() != null).map(tableTotal -> tableTotal.getTotalTransf())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getTableTotalCredit(List<WorkingDayPaymentTableDto> totals) {
		return totals.stream().filter(f -> f.getTotalCredit() != null).map(tableTotal -> tableTotal.getTotalCredit())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getTableTotalMp(List<WorkingDayPaymentTableDto> totals) {
		return totals.stream().filter(f -> f.getTotalMP() != null).map(tableTotal -> tableTotal.getTotalMP())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getTotalWorkingDay(List<WorkingDayPaymentTableDto> totals) {
		return totals.stream().filter(f -> f.getTotalWorkingDay() != null)
				.map(tableTotal -> tableTotal.getTotalWorkingDay()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public WorkingDay deleteEmployeesById(Long employeeId, Long workingDayId) {
		log.info("Buscando empleado por id");
		WorkingDay workingDay = findWorkingDayById(workingDayId);
		List<Employee> employees = workingDay.getEmployees().stream().filter(e -> e.getId() != employeeId).map(es -> es)
				.collect(Collectors.toList());
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
		return (List<WorkingDay>) workingDayRepository.findAllByOrderByDateAsc();
	}

	@Transactional
	@Override
	public void deleteWorkingDayById(Long id) {
		log.info("Delete working day by id proccess");
		if (workingDayRepository.existsById(id)) {
			WorkingDay wdToBeDeleted = findWorkingDayById(id);
			if (wdToBeDeleted.isDayStarted()) {
				log.info("Cannot delete, working day still open.");
				throw new RestoTableOpenException("No se puede eliminar, la jornada de trabajo sigue abierta.");

			} else {
				List<RestoTableClosed> tableCloseds = restoTableClosedRepository.findAllByWorkingDayId(id);
				log.info("Delete all order payment methods by resto table closeds");
				orderPaymentMethodRepository.deleteAllByRestoTableClosedIn(tableCloseds);
				log.info("Delete Working day by id");
				workingDayRepository.deleteById(id);
			}

		} else {
			throw new ItemNotFoundException("No se encontro el dia de trabajo");
		}
	}

	@Override
	public List<WorkingDay> findByEmployeesId(Long employeeId) {
		return (List<WorkingDay>) workingDayRepository.findByEmployeesId(employeeId);

	}

	

}
