package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Trim;

public interface TrimServiceInterface {
	Collection<Trim> findAll();

	Collection<Trim> findAll(int pageNumber, int pageSize);

	Trim findById(Long id);

	Trim create(Trim item);

	Trim update(Trim item);

	void delete(Long id);

	Long getCount();
}
