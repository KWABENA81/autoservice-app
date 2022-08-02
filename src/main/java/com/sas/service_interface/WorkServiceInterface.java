package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Work;

public interface WorkServiceInterface {
	Collection<Work> findAll();

	Collection<Work> findAll(int pageNumber, int pageSize);

	Work findById(Long id);

	Work create(Work w);

	Work update(Work w);

	void delete(Long id);

	Long getCount();
}
