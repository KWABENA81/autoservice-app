package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Engine;

public interface EngineServiceInterface {
	Collection<Engine> findAll();

	Collection<Engine> findAll(int pageNumber, int pageSize);

	Engine findById(Long id);

	Engine create(Engine x);

	Engine update(Engine x);

	void delete(Long id);

	Long getCount();
}
