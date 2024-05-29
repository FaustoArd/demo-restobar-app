package com.lord.arbam.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.dto.OrderPaymentMethodDto;
import com.lord.arbam.dto.OrderPaymentMethodResponse;
import com.lord.arbam.dto.PaymentMethodDto;
import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.dto.RestoTableOrderClosedDto;
import com.lord.arbam.dto.RestoTableOrderDto;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.OrderPaymentMethod;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrder;
import com.lord.arbam.model.RestoTableOrderClosed;
import com.lord.arbam.repository.OrderPaymentMethodRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.RestoTableClosedRepository;
import com.lord.arbam.repository.RestoTableOrderClosedRepository;
import com.lord.arbam.service.RestoTableClosedService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestoTableClosedServiceImpl implements RestoTableClosedService {

	@Autowired
	private final RestoTableClosedRepository restoTableClosedRepository;

	@Autowired
	private final RestoTableOrderClosedRepository restoTableOrderClosedRepository;

	@Autowired
	private OrderPaymentMethodRepository orderPaymentMethodRepository;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Override
	public RestoTableClosed findTableClosedById(Long id) {
		return restoTableClosedRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Closed table not found"));
	}

	@Override
	public RestoTableClosed saveTableClosed(RestoTableClosed restoTableClosed) {
		return restoTableClosedRepository.save(restoTableClosed);
	}

	@Override
	public void deleteTableClosedById(Long id) {
		if (restoTableClosedRepository.existsById(id)) {
			restoTableClosedRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException("Closed table not found");
		}
	}

	@Override
	public List<RestoTableClosed> findAllTablesClosed() {
		return (List<RestoTableClosed>) restoTableClosedRepository.findAll();
	}

	@Override
	public List<RestoTableClosed> findAllByWorkingDayId(Long workingDayId) {
		return (List<RestoTableClosed>) restoTableClosedRepository.findAllByWorkingDayId(workingDayId);
	}

	@Override
	public List<RestoTableClosed> findAllByWorkingDayIdOrderByTableNumberAsc(Long workingDayId) {
		return (List<RestoTableClosed>) restoTableClosedRepository
				.findAllByWorkingDayIdOrderByTableNumberAsc(workingDayId);
	}

	@Override
	public List<OrderPaymentMethodResponse> findAllPaymentsByRestoTableClosed(long restoTableClosedId) {
		RestoTableClosed tableClosed = findTableClosedById(restoTableClosedId);
		List<OrderPaymentMethod> paymentMethods = orderPaymentMethodRepository.findAllByRestoTableClosed(tableClosed);
		return mapOrderPaymentMethodsToResponses(paymentMethods, tableClosed);
	}

	private List<OrderPaymentMethodResponse> mapOrderPaymentMethodsToResponses(List<OrderPaymentMethod> paymentMethods,
			RestoTableClosed tableClosed)

	{
		return paymentMethods.stream().map(payment -> {
			
			OrderPaymentMethodResponse orderPaymentMethodResponse = new OrderPaymentMethodResponse();
			orderPaymentMethodResponse.setId(payment.getId());
			PaymentMethod paymentMethod = findPaymentMethodById(payment.getPaymentMethod().getId());
			orderPaymentMethodResponse.setPaymentMethod(mapPaymentMethodToDto(paymentMethod));
			orderPaymentMethodResponse.setPaymentTotal(payment.getPaymentTotal());
			orderPaymentMethodResponse.setRestoTableClosedDto(mapTableClosedToDto(tableClosed));
			List<RestoTableOrderClosed> orderCloseds = restoTableOrderClosedRepository
					.findAllById(payment.getOrders().stream().map(m -> m.getId()).toList());
			orderPaymentMethodResponse.setOrderCloseds(mapOrderClosedToOrderDto(orderCloseds));
			return orderPaymentMethodResponse;

		}).toList();

	}

	private static PaymentMethodDto mapPaymentMethodToDto(PaymentMethod paymentMethod) {
		PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
		paymentMethodDto.setId(paymentMethod.getId());
		paymentMethodDto.setPaymentMethod(paymentMethod.getPaymentMethod());
		return paymentMethodDto;
	}

	private static RestoTableClosedDto mapTableClosedToDto(RestoTableClosed tableClosed) {
		RestoTableClosedDto restoTableClosedDto = new RestoTableClosedDto();
		restoTableClosedDto.setId(tableClosed.getId());
		restoTableClosedDto.setEmployeeName(tableClosed.getEmployeeName());
		restoTableClosedDto.setTableNumber(tableClosed.getTableNumber());
		restoTableClosedDto.setTotalPrice(tableClosed.getTotalPrice());
		restoTableClosedDto.setWorkingDayId(tableClosed.getWorkingDay().getId());
		return restoTableClosedDto;
	}

	private static List<RestoTableOrderClosedDto> mapOrderClosedToOrderDto(List<RestoTableOrderClosed> orderCloseds) {
		List<RestoTableOrderClosedDto> ordersDto = orderCloseds.stream().map(orderClosed -> {
			RestoTableOrderClosedDto orderDto = new RestoTableOrderClosedDto();
			orderDto.setId(orderClosed.getId());
			orderDto.setProductName(orderClosed.getProductName());
			orderDto.setProductQuantity(orderClosed.getProductQuantity());
			orderDto.setTotalOrderPrice(orderClosed.getTotalOrderPrice());
			return orderDto;
		}).toList();
		return ordersDto;
	}

//	@Override
//	public List<RestoTableOrderClosed> findAllOrdersClosed(long restoTableClosedId) {
//		RestoTableClosed restoTableClosed = restoTableClosedRepository.findById(restoTableClosedId).orElseThrow(()-> new ItemNotFoundException("No se encontro la mesa cerrada"));
//		return (List<RestoTableOrderClosed>)restoTableOrderClosedRepository.findAllByRestoTableClosed(restoTableClosed);
//	}

	private PaymentMethod findPaymentMethodById(long paymentMethodId) {
		return paymentMethodRepository.findById(paymentMethodId)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el metodo de pago"));
	}

}
