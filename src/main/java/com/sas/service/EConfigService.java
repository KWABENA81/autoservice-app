package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.EConfig;
import com.sas.repository.EConfigRepository;
import com.sas.service_interface.EConfigServiceInterface;

@Service
public class EConfigService implements EConfigServiceInterface {
	@Autowired
	EConfigRepository econfigRepository;

	@Override
	public Collection<EConfig> findAll() {
		return econfigRepository.findAll();
	}

	@Override
	public Collection<EConfig> findAll(int pageNumber, int pageSize) {
		return econfigRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public EConfig findById(Long id) {
		return econfigRepository.findOne(id);
	}

	@Override
	public EConfig create(EConfig ec) {
		if (ec.getID() != null) {
			return null;
		}
		return econfigRepository.save(ec);
	}

	@Override
	public EConfig update(EConfig ec) {
		EConfig dbValue = econfigRepository.findOne(ec.getID());
		if (dbValue == null) {
			return null;
		}
		dbValue.setConfig(ec.getConfig());
		return econfigRepository.save(dbValue);
	}

	@Override
	public void delete(Long id) {
		econfigRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return econfigRepository.count();
	}

}
