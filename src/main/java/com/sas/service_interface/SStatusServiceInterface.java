package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.SStatus;

public interface SStatusServiceInterface {
	Collection<SStatus> findAll();

	Collection<SStatus> findAll(int pageNumber, int pageSize);

	SStatus findById(Long id);

	SStatus create(SStatus ss);

	SStatus update(SStatus ss);

	void delete(Long id);

	Long getCount();
}
