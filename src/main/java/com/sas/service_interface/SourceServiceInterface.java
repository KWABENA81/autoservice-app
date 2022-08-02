package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Source;

public interface SourceServiceInterface {
	Collection<Source> findAll();

	Collection<Source> findAll(int pageNumber, int pageSize);

	Source findById(Long id);

	Source create(Source ss);

	Source update(Source ss);

	void delete(Long id);

	Long getCount();
}
