package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Fuels;
import com.sas.repository.FuelRepository;
import com.sas.service_interface.FuelServiceInterface;

@Service
public class FuelService implements FuelServiceInterface {
	@Autowired
	FuelRepository fuelRepository;

	@Override
	public Collection<Fuels> findAll() {
		return fuelRepository.findAll();
	}

	@Override
	public Collection<Fuels> findAll(int pageNumber, int pageSize) {
		return fuelRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Fuels findById(Long id) {
		return fuelRepository.findOne(id);
	}

	@Override
	public Fuels create(Fuels fuel) {
		if (fuel.getID() != null) {
			return null;
		}
		return fuelRepository.save(fuel);
	}

	@Override
	public Fuels update(Fuels fuel) {
		Fuels dbValue = fuelRepository.findOne(fuel.getID());
		if (dbValue == null) {
			return null;
		}
		dbValue.setEDescs(fuel.getEDescs());
		dbValue.setFuel(fuel.getFuel());
		//
		return fuelRepository.save(dbValue);
	}

	@Override
	public void delete(Long id) {
		fuelRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return fuelRepository.count();
	}

}
