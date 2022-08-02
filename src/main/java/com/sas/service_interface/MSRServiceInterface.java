package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.MSR;

public interface MSRServiceInterface {
	Collection<MSR> findAll();

	Collection<MSR> findAll(int pageNumber, int pageSize);

	MSR findById(Long id);

	MSR create(MSR e);

	MSR update(MSR e);

	void delete(Long id);

	Long getCount();
}
