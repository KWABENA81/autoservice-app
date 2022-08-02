package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Insignia;

public interface TrimNameServiceInterface {
	Collection<Insignia> findAll();

	Collection<Insignia> findAll(int pageNumber, int pageSize);

	Insignia findById(Long id);

	Insignia create(Insignia item);

	Insignia update(Insignia item);

	void delete(Long id);

	Long getCount();
}
