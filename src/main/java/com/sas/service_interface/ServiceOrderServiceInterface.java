package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.ServiceOrder;

public interface ServiceOrderServiceInterface {
	Collection<ServiceOrder> findAll();

	Collection<ServiceOrder> findAll(int pageNumber, int pageSize);

	ServiceOrder findById(Long id);

	ServiceOrder create(ServiceOrder ss);

	ServiceOrder update(ServiceOrder ss);

	void delete(Long id);

	Long getCount();
}
