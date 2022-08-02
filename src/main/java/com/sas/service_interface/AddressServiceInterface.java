package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Address;

public interface AddressServiceInterface {
	Collection<Address> findAll();

	Collection<Address> findAll(int pageNumber, int pageSize);

	Address findById(Long id);

	Address create(Address address);

	Address update(Address address);

	void delete(Long id);

	Long getCount();
}
