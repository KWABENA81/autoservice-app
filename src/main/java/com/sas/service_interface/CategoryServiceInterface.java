package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Category;

public interface CategoryServiceInterface {
	Collection<Category> findAll();

	Collection<Category> findAll(int pageNumber, int pageSize);

	Category findById(Long id);

	Category create(Category x);

	Category update(Category x);

	void delete(Long id);

	Long getCount();
}
