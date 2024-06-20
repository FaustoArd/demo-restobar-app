package com.lord.arbam.service_impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lord.arbam.custom_mapper.RestoTableServiceCustomMapper;
import com.lord.arbam.dto.OrderPaymentMethodDto;
import com.lord.arbam.dto.OrderPaymentMethodResponse;
import com.lord.arbam.dto.PaymentMethodDto;
import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.dto.RestoTableDto;
import com.lord.arbam.dto.RestoTableOrderDto;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.RestoTableOpenException;
import com.lord.arbam.mapper.RestoTableClosedMapper;
import com.lord.arbam.mapper.RestoTableMapper;
import com.lord.arbam.mapper.RestoTableOrderMapper;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.OrderPaymentMethod;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrder;
import com.lord.arbam.model.RestoTableOrderClosed;
import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.repository.EmployeeRepository;
import com.lord.arbam.repository.OrderPaymentMethodRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.ProductRepository;
import com.lord.arbam.repository.RestoTableClosedRepository;
import com.lord.arbam.repository.RestoTableOrderClosedRepository;
import com.lord.arbam.repository.RestoTableOrderRepository;
import com.lord.arbam.repository.RestoTableRepository;
import com.lord.arbam.repository.WorkingDayRepository;
import com.lord.arbam.service.RestoTableOrderService;
import com.lord.arbam.service.RestoTableService;

import ch.qos.logback.core.joran.conditional.Condition;
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

	@Autowired
	private final OrderPaymentMethodRepository orderPaymentMethodRepository;

	@Autowired
	private final RestoTableServiceCustomMapper restoTableServiceCustomMapper;
	
	@Autowired
	private final ProductRepository productRepository;
	
	@Autowired
	private final RestoTableOrderService restoTableOrderService;

	@Override
	public RestoTable findRestoTableById(Long id) {
		return restoTableRepository.findById(id).orElseThrow(
				() -> new ItemNotFoundException("Table not found. RestoTableServiceImpl.findRestoTableById"));
	}

	@Override
	public List<RestoTable> findAllRestoTables() {
		return (List<RestoTable>) restoTableRepository.findAll();
	}

	@Override
	public RestoTable openRestoTable(RestoTable restoTable) {
		log.info("Open table");
		RestoTable newRestotable = findRestoTableById(restoTable.getId());
		Employee employee = findEmployeeById(restoTable.getEmployee().getId());
		newRestotable.setEmployee(employee);
		newRestotable.setTableNumber(restoTable.getTableNumber());
		newRestotable.setTableDescription(restoTable.getTableDescription());
		newRestotable.setOpen(true);
		newRestotable.setTotalTablePrice(new BigDecimal(0));
		return restoTableRepository.save(newRestotable);

	}

	@Override
	public RestoTable updateRestoTable(RestoTable restoTable) {
		log.info("Update table");
		RestoTable updatedRestotable = findRestoTableById(restoTable.getId());
		Employee employee = findEmployeeById(restoTable.getEmployee().getId());
		updatedRestotable.setEmployee(employee);
		updatedRestotable.setTableNumber(restoTable.getTableNumber());
		updatedRestotable.setTableDescription(restoTable.getTableDescription());
		return restoTableRepository.save(updatedRestotable);
	}

	@Override
	public RestoTable updateRestoTableTotalPrice(RestoTable restoTable, List<RestoTableOrder> orders) {
		log.info("Finding orders total price");
		ListIterator<RestoTableOrder> ordersIt = orders.listIterator();
		Double updatedPrice = 0.00;
		log.info("Adding orders total price");
		while (ordersIt.hasNext()) {
			updatedPrice += ordersIt.next().getTotalOrderPrice().doubleValue();
		}
		log.info("Updating resto table total price");
		restoTable.setTotalTablePrice(new BigDecimal(updatedPrice));
		return restoTableRepository.save(restoTable);
	}

	@Transactional
	@Override
	public RestoTableClosedDto closeRestoTable(Long restoTableId, Long workingDayId,
			List<OrderPaymentMethodDto> orderPaymentMethodDtos) {
		log.info("Starting close resto table id: " + restoTableId);
		RestoTable findedTable = findRestoTableById(restoTableId);
		Employee employee = findEmployeeById(findedTable.getEmployee().getId());
		WorkingDay workingDay = findworkingDayById(workingDayId);
		log.info("Copying table, id: " + restoTableId + " data");
		RestoTableClosed tableClosed = restoTableServiceCustomMapper.buildTableClosed(findedTable, employee, workingDay,
				orderPaymentMethodDtos);
		RestoTableClosed savedTableClosed = restoTableClosedRepository.save(tableClosed);
		List<OrderPaymentMethod> paymentMethods = getAllOrderPaymentMethods(orderPaymentMethodDtos, savedTableClosed);
		List<OrderPaymentMethod> savedOrderPaymentMethods = orderPaymentMethodRepository.saveAll(paymentMethods);
		List<RestoTableOrder> orders = restoTableOrderRepository.findAllByRestoTableId(restoTableId);
		log.info("Deleting orders from Table id: " + restoTableId);
		restoTableOrderRepository.deleteAll(orders);
		log.info("Saving new default table");
		restoTableRepository.save(restoTableServiceCustomMapper.setRestoTableToDefault(findedTable));
		log.info("Map RestoTableClosed to Dto and return");
		RestoTableClosedDto restoTableClosedDto = RestoTableClosedMapper.INSTANCE.toTableClosedDto(savedTableClosed);
		restoTableClosedDto
				.setOrderPaymentMethodResponses(mapPaymentsToResponses(savedOrderPaymentMethods, savedTableClosed));
		return restoTableClosedDto;

	}

	private List<OrderPaymentMethodResponse> mapPaymentsToResponses(List<OrderPaymentMethod> savedOrderPaymentMethods,
			RestoTableClosed savedTableClosed) {
		List<OrderPaymentMethodResponse> paymentResponses = savedOrderPaymentMethods.stream().map(payment -> {
			OrderPaymentMethodResponse paymentResponse = new OrderPaymentMethodResponse();
			paymentResponse.setId(payment.getId());
			PaymentMethod paymentMethod = findPaymentMethodById(payment.getPaymentMethod().getId());
			paymentResponse.setPaymentMethod(mapToPaymentMethodDto(paymentMethod));
			paymentResponse.setPaymentTotal(payment.getPaymentTotal());
			paymentResponse.setRestoTableClosedDto(RestoTableClosedMapper.INSTANCE.toTableClosedDto(savedTableClosed));
			List<RestoTableOrderClosed> orderCloseds = restoTableOrderClosedRepository
					.findAllById(payment.getOrders().stream().map(m -> m.getId()).toList());
			paymentResponse.setOrderCloseds(restoTableServiceCustomMapper.mapOrderClosedsToDtos(orderCloseds));
			return paymentResponse;
		}).toList();
		return paymentResponses;
	}

	private List<OrderPaymentMethod> getAllOrderPaymentMethods(List<OrderPaymentMethodDto> orderPaymentMethodDtos,
			RestoTableClosed tableClosed) {
		List<OrderPaymentMethod> orderPaymentMethods = orderPaymentMethodDtos.stream().map(paymentDto -> {
			PaymentMethod paymentMethod = findPaymentMethodByPaymentMethod(
					paymentDto.getPaymentMethod().getPaymentMethod());
			OrderPaymentMethod orderPaymentMethod = new OrderPaymentMethod();
			orderPaymentMethod.setPaymentMethod(paymentMethod);
			orderPaymentMethod.setPaymentTotal(paymentDto.getPaymentTotal());
			orderPaymentMethod.setOrders(mapOrdersToOrderClosedList(paymentDto.getOrders()));
			orderPaymentMethod.setRestoTableClosed(tableClosed);

			return orderPaymentMethod;
		}).toList();

		return orderPaymentMethods;
	}

	private List<RestoTableOrderClosed> mapOrdersToOrderClosedList(List<RestoTableOrderDto> orders) {
		List<RestoTableOrderClosed> orderClosedList = orders.stream().map(orderDto -> {

			RestoTableOrderClosed orderClosed = new RestoTableOrderClosed();
			orderClosed.setProductName(orderDto.getProductName());
			orderClosed.setProductQuantity(orderDto.getProductQuantity());
			orderClosed.setTotalOrderPrice(orderDto.getTotalOrderPrice());
			return orderClosed;
		}).toList();
		return restoTableOrderClosedRepository.saveAll(orderClosedList);
	}

	@Override
	public List<RestoTable> findAllByOrderByIdAsc() {
		return (List<RestoTable>) restoTableRepository.findAllByOrderByIdAsc();
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
	public List<PaymentMethod> findAllPaymentMethods() {
		return (List<PaymentMethod>) paymentMethodRepository.findAll();
	}

	@Override
	public void checkTablesOpen() {
		if (findAllRestoTables().stream().filter(r -> r.isOpen()).findFirst().isPresent()) {
			log.warn("Cannot close Working Day because they are one or More tables open.");
			throw new RestoTableOpenException(
					"No se puede cerrar la jornada de trabajo porque hay una o mas mesas abiertas.");
		}

	}
	
	@Override
	@Transactional
	public RestoTableDto copyRemainingTable(RestoTableDto restoTableDto) {
		//long restoTableId = restoTableOrderDtos.stream().map(m -> m.getRestoTableId()).findAny().get().longValue();
		BigDecimal totalTablePrice = restoTableDto.getRestoTableOrders().stream().map(m -> m.getTotalOrderPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
		RestoTable originTable = RestoTableMapper.INSTANCE.toRestoTable(restoTableDto);
		RestoTable destinationTable = mapOriginToDestinationTable( restoTableDto.getRestoTableOrders(), originTable, totalTablePrice);
		RestoTable copiedTable = restoTableRepository.save(destinationTable);
		 return  RestoTableMapper.INSTANCE.toRestotableDto(copiedTable);
		
		
	}
	
	private RestoTable mapOriginToDestinationTable(List<RestoTableOrderDto> restoTableOrderDtos,RestoTable originTable,BigDecimal totalTablePrice) {
		return  restoTableRepository.findById(originTable.getId()).map(dest -> {
			dest.setTableOrders(mapRemainingOrders(restoTableOrderDtos,dest));
			dest.setTableNumber(originTable.getTableNumber());
			if(originTable.getTableDescription()==null) {
				originTable.setTableDescription("");
			}
			dest.setTableDescription(originTable.getTableDescription() + "-P-");
			dest.setOpen(true);
			Employee employee = findEmployeeById(originTable.getEmployee().getId());
			dest.setEmployee(employee);
			dest.setCopy(true);
			dest.setTotalTablePrice(totalTablePrice);
			return dest;
			}).get();
	}

	private Set<RestoTableOrder> mapRemainingOrders(List<RestoTableOrderDto> restoTableOrderDtos,RestoTable destinationTable) {
		Set<RestoTableOrder> orders =  restoTableOrderDtos.stream().map(orderDto -> {
			RestoTableOrder order = RestoTableOrderMapper.INSTANCE.toOrder(orderDto);
			order.setRestoTable(destinationTable);
			log.info("Save order");
			return restoTableOrderService.createOrder(order);
		}).collect(Collectors.toSet());
		
		return restoTableOrderRepository.saveAll(orders).stream().collect(Collectors.toSet());
	}
	
	
	private Employee findEmployeeById(long employeeId) {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el empleado"));
	}

	private WorkingDay findworkingDayById(long workingDayId) {
		return workingDayRepository.findById(workingDayId)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el dia de trabajo"));
	}

	private PaymentMethod findPaymentMethodByPaymentMethod(String paymentMethod) {
		return paymentMethodRepository.findByPaymentMethod(paymentMethod)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el metodo de pago"));
	}

	private PaymentMethod findPaymentMethodById(long paymentMethodId) {
		return paymentMethodRepository.findById(paymentMethodId)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el metodo de pago."));
	}
	
	private Product findProductbyId(long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new ItemNotFoundException("No se encontro el producto."));
	}

	private PaymentMethodDto mapToPaymentMethodDto(PaymentMethod paymentMethod) {

		PaymentMethodDto dto = new PaymentMethodDto();
		dto.setId(paymentMethod.getId());
		dto.setPaymentMethod(paymentMethod.getPaymentMethod());
		return dto;
	}

	



	

}
