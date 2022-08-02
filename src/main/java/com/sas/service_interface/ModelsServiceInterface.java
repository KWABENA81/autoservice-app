package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.EConfig;
import com.sas.entity.Models;

public interface ModelsServiceInterface {
	Collection<Models> findAll();

	Collection<Models> findAll(int pageNumber, int pageSize);

	Models findById(Long id);

	Models create(Models e);

	Models update(Models e);

	void delete(Long id);

	Long getCount();
}
