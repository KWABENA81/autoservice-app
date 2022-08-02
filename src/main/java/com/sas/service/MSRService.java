package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.MSR;
import com.sas.repository.MSRRepository;
import com.sas.service_interface.MSRServiceInterface;

@Service
public class MSRService implements MSRServiceInterface {
	@Autowired
	MSRRepository msrRepository;

	@Override
	public Collection<MSR> findAll() {
		return msrRepository.findAll();
	}

	@Override
	public Collection<MSR> findAll(int pageNumber, int pageSize) {
		return msrRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public MSR findById(Long id) {
		return msrRepository.findOne(id);
	}

	@Override
	public MSR create(MSR m) {
		if (m.getID() != null) {
			return null;
		}
		return msrRepository.save(m);
	}

	@Override
	public MSR update(MSR m) {
		MSR origMSR = msrRepository.findOne(m.getID());
		if (origMSR == null) {
			return null;
		}
		return msrRepository.save(m);
	}

	@Override
	public void delete(Long id) {
		msrRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return msrRepository.count();
	}

}
