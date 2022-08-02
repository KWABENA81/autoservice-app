package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Make;

public interface MakeServiceInterface {
	Collection<Make> findAll();

	Collection<Make> findAll(int pageNumber, int pageSize);

	Make findById(Long id);

	Make create(Make m);

	Make update(Make m);

	void delete(Long id);

	Long getCount();
}
