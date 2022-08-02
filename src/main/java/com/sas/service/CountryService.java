package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Country;
import com.sas.repository.CountryRepository;
import com.sas.service_interface.CountryServiceInterface;

@Service
public class CountryService implements CountryServiceInterface {
	@Autowired
	CountryRepository countryRepository;

	@Override
	public Collection<Country> findAll() {
		return countryRepository.findAll();
	}

	@Override
	public Collection<Country> findAll(int pageNumber, int pageSize) {
		return countryRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Country findById(Long id) {
		return countryRepository.findOne(id);
	}

	@Override
	public Country create(Country c) {
		if (c.getID() != null) {
			return null;
		}
		return countryRepository.save(c);
	}

	@Override
	public Country update(Country c) {
		Country orig = countryRepository.findOne(c.getID());
		if (orig == null) {
			return null;
		}
		return countryRepository.save(c);
	}

	@Override
	public void delete(Long id) {
		countryRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return countryRepository.count();
	}
}
