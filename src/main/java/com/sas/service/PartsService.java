package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Parts;
import com.sas.repository.PartsRepository;
import com.sas.service_interface.PartsServiceInterface;

@Service
public class PartsService implements PartsServiceInterface {
	@Autowired
	PartsRepository partsRepository;

	@Override
	public Collection<Parts> findAll() {
		return partsRepository.findAll();
	}

	@Override
	public Collection<Parts> findAll(int pageNumber, int pageSize) {
		return partsRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Parts findById(Long id) {
		return partsRepository.findOne(id);
	}

	@Override
	public Parts create(Parts prts) {
		if (prts.getID() != null) {
			return null;
		}
		return partsRepository.save(prts);
	}

	@Override
	public Parts update(Parts parts) {
		Parts originalParts = partsRepository.findOne(parts.getID());
		if (originalParts == null) {
			return null;
		}
		originalParts.setDescription(parts.getDescription());
		originalParts.setInventory(parts.getInventory());
		originalParts.setPrice(parts.getPrice());
		originalParts.setQuantity(parts.getQuantity());
		//
		return partsRepository.save(originalParts);
	}

	@Override
	public void delete(Long id) {
		partsRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return partsRepository.count();
	}

	

}
