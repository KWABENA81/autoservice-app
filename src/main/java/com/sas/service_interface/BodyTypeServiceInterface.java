package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.BodyType;

public interface BodyTypeServiceInterface {
	Collection<BodyType> findAll();

	Collection<BodyType> findAll(int pageNumber, int pageSize);

	BodyType findById(Long id);

	BodyType create(BodyType c);

	BodyType update(BodyType c);

	void delete(Long id);

	Long getCount();
}
