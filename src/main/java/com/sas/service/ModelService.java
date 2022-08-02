package com.sas.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Make;
import com.sas.entity.Model;
import com.sas.repository.ModelRepository;
import com.sas.service_interface.ModelServiceInterface;

@Service
public class ModelService implements ModelServiceInterface {
	@Autowired
	ModelRepository modelRepository;

	@Override
	public Collection<Model> findAll() {
		return modelRepository.findAll();
	}

	@Override
	public Collection<Model> findAll(int pageNumber, int pageSize) {
		return modelRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Model findById(Long id) {
		return modelRepository.findOne(id);
	}

	@Override
	public Model create(Model m) {
		if (m.getID() != null) {
			return null;
		}
		Model mdl = modelRepository.save(m);
		return mdl;
	}

	@Override
	public Model update(Model m) {
		Model origModel = modelRepository.findOne(m.getID());
		if (origModel == null) {
			return null;
		}
		return modelRepository.save(m);
	}

	@Override
	public void delete(Long id) {
		modelRepository.delete(id);
	}

	/**
	 * 
	 */
	@Override
	public Long getCount() {
		return modelRepository.count();
	}

	/**
	 * 
	 * @param mname
	 * @param make
	 * @param trim
	 * @param engine
	 * @return
	 */
	public Model findByNameYearMake(String mname, Make make, String trim, String engine) {
		return modelRepository.findByNameMakeTrimEngine(mname, make, trim, engine);
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public List<Model> findByName(String model) {
		return modelRepository.findByName(model);
	}

}
