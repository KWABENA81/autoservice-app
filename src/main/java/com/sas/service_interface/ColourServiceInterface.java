package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Colours;

public interface ColourServiceInterface {
	Collection<Colours> findAll();

	Collection<Colours> findAll(int pageNumber, int pageSize);

	Colours findById(Long id);

	Colours create(Colours c);

	Colours update(Colours c);

	void delete(Long id);

	Long getCount();
}
