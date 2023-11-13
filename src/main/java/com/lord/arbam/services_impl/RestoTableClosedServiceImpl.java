package com.lord.arbam.services_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.RestoTableClosed;
import com.lord.arbam.repositories.RestoTableClosedRepository;
import com.lord.arbam.services.RestoTableClosedService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestoTableClosedServiceImpl implements RestoTableClosedService {

	@Autowired
	private final RestoTableClosedRepository restoTableClosedRepository;

	@Override
	public RestoTableClosed findRestoTableClosedById(Long id) {
		return restoTableClosedRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(
				"No se encontraron datos de la mesa cerrada. RestoTableClosedServiceImpl.findRestoTableClosedById"));
	}

	@Override
	public RestoTableClosed saveRestoTableClosed(RestoTableClosed restoTableClosed) {
		return restoTableClosedRepository.save(restoTableClosed);
	}

	@Override
	public void deleteRestoTableClosedById(Long id) {
		if (restoTableClosedRepository.existsById(id)) {
			restoTableClosedRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException(
					"No se encontro la mesa cerrada. RestoTableClosedServiceImpl.findRestoTableClosedById");
		}
	}

	@Override
	public List<RestoTableClosed> findAllRestoTablesClosed() {
		return (List<RestoTableClosed>) restoTableClosedRepository.findAll();
	}

}
