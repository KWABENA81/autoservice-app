package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Country;

public interface CountryServiceInterface {
	Collection<Country> findAll();

	Collection<Country> findAll(int pageNumber, int pageSize);

	Country findById(Long id);

	Country create(Country c);

	Country update(Country c);

	void delete(Long id);

	Long getCount();
}
