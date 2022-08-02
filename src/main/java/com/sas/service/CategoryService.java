package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Category;
import com.sas.repository.CategoryRepository;
import com.sas.service_interface.CategoryServiceInterface;

@Service
public class CategoryService implements CategoryServiceInterface {
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Collection<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Collection<Category> findAll(int pageNumber, int pageSize) {
		return categoryRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Category findById(Long id) {
		return categoryRepository.findOne(id);
	}

	@Override
	public Category create(Category cat) {
		if (cat.getID() != null) {
			return null;
		}
		return categoryRepository.save(cat);
	}

	@Override
	public Category update(Category cat) {
		Category origCategory = categoryRepository.findOne(cat.getID());
		if (origCategory == null) {
			return null;
		}
		origCategory.setDescription(cat.getDescription());
		return categoryRepository.save(cat);
	}

	@Override
	public void delete(Long id) {
		categoryRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return categoryRepository.count();
	}

}
