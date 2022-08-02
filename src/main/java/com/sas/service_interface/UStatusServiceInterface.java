package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.UStatus;

public interface UStatusServiceInterface {
	Collection<UStatus> findAll();

	Collection<UStatus> findAll(int pageNumber, int pageSize);

	UStatus findOne(Long id);

	UStatus create(UStatus address);

	UStatus update(UStatus address);

	void delete(Long id);

	Long getCount();

	UStatus findByStatus(String str);
}
