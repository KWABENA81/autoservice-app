package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.EConfig;

public interface EConfigServiceInterface {
	Collection<EConfig> findAll();

	Collection<EConfig> findAll(int pageNumber, int pageSize);

	EConfig findById(Long id);

	EConfig create(EConfig e);

	EConfig update(EConfig e);

	void delete(Long id);

	Long getCount();
}
