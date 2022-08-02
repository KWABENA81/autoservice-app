package com.sas.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Models;
import com.sas.repository.ModelsRepository;
import com.sas.service_interface.ModelsServiceInterface;

@Service
public class ModelsService implements ModelsServiceInterface {
	@Autowired
	ModelsRepository modelsRepository;

	@Override
	public Collection<Models> findAll() {
		return modelsRepository.findAll();
	}

	@Override
	public Collection<Models> findAll(int pageNumber, int pageSize) {
		return modelsRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Models findById(Long id) {
		return modelsRepository.findOne(id);
	}

	@Override
	public Models create(Models m) {
		if (m.getID() != null) {
			return null;
		}
		return modelsRepository.save(m);
	}

	@Override
	public Models update(Models m) {
		Models origModelNames = modelsRepository.findOne(m.getID());
		if (origModelNames == null) {
			return null;
		}
		return modelsRepository.save(m);
	}

	@Override
	public void delete(Long id) {
		modelsRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return modelsRepository.count();
	}

	public List<Models> findByMakeName(String nref) {
		return modelsRepository.findByMakeName(nref);
	}

}
