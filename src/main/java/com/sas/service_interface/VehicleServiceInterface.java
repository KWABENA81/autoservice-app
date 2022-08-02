package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Vehicle;

public interface VehicleServiceInterface {
	Collection<Vehicle> findAll();

	Collection<Vehicle> findAll(int pageNumber, int pageSize);

	Vehicle findOne(Long id);

	Vehicle create(Vehicle v);

	Vehicle update(Vehicle v);

	void delete(Long id);

	Long getCount();

	// Vehicle findByStatus(String str);
}
