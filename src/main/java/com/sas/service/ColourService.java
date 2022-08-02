package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Colours;
import com.sas.repository.ColourRepository;
import com.sas.service_interface.ColourServiceInterface;

@Service
public class ColourService implements ColourServiceInterface {
	@Autowired
	ColourRepository colourRepository;

	@Override
	public Collection<Colours> findAll() {
		return colourRepository.findAll();
	}

	@Override
	public Collection<Colours> findAll(int pageNumber, int pageSize) {
		return colourRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Colours findById(Long id) {
		return colourRepository.findOne(id);
	}

	@Override
	public Colours create(Colours col) {
		if (col.getID() != null) {
			return null;
		}
		return colourRepository.save(col);
	}

	@Override
	public Colours update(Colours col) {
		Colours dbValue = colourRepository.findOne(col.getID());
		if (dbValue == null) {
			return null;
		}
		dbValue.setCCode(col.getCCode());
		dbValue.setColour(col.getColour());
		//
		return colourRepository.save(dbValue);
	}

	@Override
	public void delete(Long id) {
		colourRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return colourRepository.count();
	}

}
