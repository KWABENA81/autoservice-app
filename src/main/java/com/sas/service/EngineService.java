package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Engine;
import com.sas.repository.EngineRepository;
import com.sas.service_interface.EngineServiceInterface;

@Service
public class EngineService implements EngineServiceInterface {
	@Autowired
	EngineRepository engineRepository;

	@Override
	public Collection<Engine> findAll() {
		return engineRepository.findAll();
	}

	@Override
	public Collection<Engine> findAll(int pageNumber, int pageSize) {
		return engineRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Engine findById(Long id) {
		return engineRepository.findOne(id);
	}

	@Override
	public Engine create(Engine eng) {
		if (eng.getID() != null) {
			return null;
		}
		return engineRepository.save(eng);
	}

	@Override
	public Engine update(Engine eng) {
		Engine orig = engineRepository.findOne(eng.getID());
		if (orig == null) {
			return null;
		}
		return engineRepository.save(eng);
	}

	@Override
	public void delete(Long id) {
		engineRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return engineRepository.count();
	}

	/**
	 * 
	 * @param strEng
	 * @return
	 */
	public Engine findByDescription(String strEng) {
		return engineRepository.findByAuxDesc(strEng);
	}

}
