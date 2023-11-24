package com.lord.arbam.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.repository.RestoTableClosedRepository;
import com.lord.arbam.service.RestoTableClosedService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestoTableClosedServiceImpl implements RestoTableClosedService {

	@Autowired
	private final RestoTableClosedRepository restoTableClosedRepository;

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
	public List<RestoTableClosed> findAllByWorkingDayId(Long id) {
		return (List<RestoTableClosed>)restoTableClosedRepository.findAllByWorkingDayId(id);
	}

}
