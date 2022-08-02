package com.sas.service_interface;

import java.util.Collection;
import java.util.List;

import com.sas.entity.Country;
import com.sas.entity.Region;

public interface RegionServiceInterface {
	Collection<Region> findAll();

	Collection<Region> findAll(int pageNumber, int pageSize);

	Region findById(Long id);

	Region create(Region r);

	Region update(Region r);

	void delete(Long id);

	Long getCount();

	List<Region> findByCountry(Country country);
}
