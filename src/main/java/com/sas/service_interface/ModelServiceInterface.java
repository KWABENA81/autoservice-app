package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Model;

public interface ModelServiceInterface {
	Collection<Model> findAll();

	Collection<Model> findAll(int pageNumber, int pageSize);

	Model findById(Long id);

	Model create(Model m);

	Model update(Model m);

	void delete(Long id);

	Long getCount();
}
