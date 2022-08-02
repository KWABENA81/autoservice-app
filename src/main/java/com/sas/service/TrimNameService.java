package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Insignia;
import com.sas.repository.TrimNameRepository;
import com.sas.service_interface.TrimNameServiceInterface;

@Service
public class TrimNameService implements TrimNameServiceInterface {
	@Autowired
	TrimNameRepository trimNameRepository;

	@Override
	public Collection<Insignia> findAll() {
		return trimNameRepository.findAll();
	}

	@Override
	public Collection<Insignia> findAll(int pageNumber, int pageSize) {
		return trimNameRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Insignia findById(Long id) {
		return trimNameRepository.findOne(id);
	}

	@Override
	public Insignia create(Insignia trim) {
		if (trim.getID() != null) {
			return null;
		}
		return trimNameRepository.save(trim);
	}

	@Override
	public Insignia update(Insignia m) {
		Insignia orig = trimNameRepository.findOne(m.getID());
		if (orig == null) {
			return null;
		}
		return trimNameRepository.save(m);
	}

	@Override
	public void delete(Long id) {
		trimNameRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return trimNameRepository.count();
	}

}
