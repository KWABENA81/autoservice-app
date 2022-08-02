package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Parts;

public interface PartsServiceInterface {
	Collection<Parts> findAll();

	Collection<Parts> findAll(int pageNumber, int pageSize);

	Parts findById(Long id);

	Parts create(Parts ss);

	Parts update(Parts ss);

	void delete(Long id);

	Long getCount();

}
