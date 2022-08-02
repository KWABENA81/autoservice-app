package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Trim;
import com.sas.repository.TrimRepository;
import com.sas.service_interface.TrimServiceInterface;

@Service
public class TrimService implements TrimServiceInterface {
	@Autowired
	TrimRepository trimRepository;

	@Override
	public Collection<Trim> findAll() {
		return trimRepository.findAll();
	}

	@Override
	public Collection<Trim> findAll(int pageNumber, int pageSize) {
		return trimRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Trim findById(Long id) {
		return trimRepository.findOne(id);
	}

	@Override
	public Trim create(Trim trim) {
		if (trim.getID() != null) {
			return null;
		}
		return trimRepository.save(trim);
	}

	@Override
	public Trim update(Trim m) {
		Trim orig = trimRepository.findOne(m.getID());
		if (orig == null) {
			return null;
		}
		return trimRepository.save(m);
	}

	@Override
	public void delete(Long id) {
		trimRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return trimRepository.count();
	}

	public Trim findByDescription(String strim) {
		return trimRepository.findByDescription(strim);
	}

}
