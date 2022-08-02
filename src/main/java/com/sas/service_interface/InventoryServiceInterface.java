package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Inventory;

public interface InventoryServiceInterface {
	Collection<Inventory> findAll();

	Collection<Inventory> findAll(int pageNumber, int pageSize);

	Inventory findById(Long id);

	Inventory create(Inventory x);

	Inventory update(Inventory x);

	void delete(Long id);

	Long getCount();
}
