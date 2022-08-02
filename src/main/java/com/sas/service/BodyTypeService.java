package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.BodyType;
import com.sas.repository.BodyTypeRepository;
import com.sas.service_interface.BodyTypeServiceInterface;

@Service
public class BodyTypeService implements BodyTypeServiceInterface {
	@Autowired
	BodyTypeRepository bodyTypeRepository;

	@Override
	public Collection<BodyType> findAll() {
		return bodyTypeRepository.findAll();
	}

	@Override
	public Collection<BodyType> findAll(int pageNumber, int pageSize) {
		return bodyTypeRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public BodyType findById(Long id) {
		return bodyTypeRepository.findOne(id);
	}

	@Override
	public BodyType create(BodyType bt) {
		if (bt.getID() != null) {
			return null;
		}
		return bodyTypeRepository.save(bt);
	}

	@Override
	public BodyType update(BodyType bt) {
		BodyType bodyType = bodyTypeRepository.findOne(bt.getID());
		if (bodyType == null) {
			return null;
		}
		bodyType.setBType(bt.getBType());
		return bodyTypeRepository.save(bodyType);
	}

	@Override
	public void delete(Long id) {
		bodyTypeRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return bodyTypeRepository.count();
	}

}
