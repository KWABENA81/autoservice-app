package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.DriveType;

public interface DriveTypeServiceInterface {
	Collection<DriveType> findAll();

	Collection<DriveType> findAll(int pageNumber, int pageSize);

	DriveType findById(Long id);

	DriveType create(DriveType x);

	DriveType update(DriveType x);

	void delete(Long id);

	Long getCount();
}
