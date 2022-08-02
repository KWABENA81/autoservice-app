package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Source;
import com.sas.repository.SourceRepository;
import com.sas.service_interface.SourceServiceInterface;

@Service
public class SourceService implements SourceServiceInterface {
	@Autowired
	SourceRepository sourceRepository;

	@Override
	public Collection<Source> findAll() {
		return sourceRepository.findAll();
	}

	@Override
	public Collection<Source> findAll(int pageNumber, int pageSize) {
		return sourceRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Source findById(Long id) {
		return sourceRepository.findOne(id);
	}

	@Override
	public Source create(Source ss) {
		if (ss.getID() != null) {
			return null;
		}
		return sourceRepository.save(ss);
	}

	@Override
	public Source update(Source source) {
		Source storedSource = sourceRepository.findOne(source.getID());
		if (storedSource == null) {
			return null;
		}
		storedSource.setAddress(source.getAddress());
		storedSource.setContact(source.getContact());
		storedSource.setEmail(source.getEmail());
		storedSource.setPhone(source.getPhone());
		storedSource.setPhoneOther(source.getPhoneOther());
		storedSource.setReference(source.getReference());
		return sourceRepository.save(storedSource);
	}

	@Override
	public void delete(Long id) {
		sourceRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return sourceRepository.count();
	}

}
