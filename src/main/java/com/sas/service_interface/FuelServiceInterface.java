package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Fuels;

public interface FuelServiceInterface {
	Collection<Fuels> findAll();

	Collection<Fuels> findAll(int pageNumber, int pageSize);

	Fuels findById(Long id);

	Fuels create(Fuels f);

	Fuels update(Fuels f);

	void delete(Long id);

	Long getCount();
}
