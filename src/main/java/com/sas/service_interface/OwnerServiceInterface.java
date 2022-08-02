package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Owner;

public interface OwnerServiceInterface {
	Collection<Owner> findAll();

	Collection<Owner> findAll(int pageNumber, int pageSize);

	Owner findById(Long id);

	Owner create(Owner owr);

	Owner update(Owner owr);

	void delete(Long id);

	Long getCount();

	//Owner findByNameAndPhone(String name, String phone);
}
