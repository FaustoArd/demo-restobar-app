package com.lord.arbam.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrderClosed;
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
	
	@Override
	public RestoTableClosed findTableClosedById(Long id) {
		return restoTableClosedRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(
				"Closed table not found"));
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
			throw new ItemNotFoundException(
					"Closed table not found");
		}
	}

	@Override
	public List<RestoTableClosed> findAllTablesClosed() {
		return (List<RestoTableClosed>) restoTableClosedRepository.findAll();
	}

	@Override
	public List<RestoTableClosed> findAllByWorkingDayId(Long workingDayId) {
		return (List<RestoTableClosed>)restoTableClosedRepository.findAllByWorkingDayId(workingDayId);
	}

	@Override
	public List<RestoTableClosed> findAllByWorkingDayIdOrderByTableNumberAsc(Long workingDayId) {
		return (List<RestoTableClosed>)restoTableClosedRepository.findAllByWorkingDayIdOrderByTableNumberAsc(workingDayId);
	}

	@Override
	public List<RestoTableOrderClosed> findAllOrdersClosed(long restoTableClosedId) {
		RestoTableClosed restoTableClosed = restoTableClosedRepository.findById(restoTableClosedId).orElseThrow(()-> new ItemNotFoundException("No se encontro la mesa cerrada"));
		return (List<RestoTableOrderClosed>)restoTableOrderClosedRepository.findAllByRestoTableClosed(restoTableClosed);
	}

}
