package com.sas.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Make;
import com.sas.repository.MakeRepository;
import com.sas.service_interface.MakeServiceInterface;

@Service
public class MakeService implements MakeServiceInterface {
	@Autowired
	MakeRepository makeRepository;

	@Override
	public Collection<Make> findAll() {
		return makeRepository.findAll();
	}

	@Override
	public Collection<Make> findAll(int pageNumber, int pageSize) {
		return makeRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Make findById(Long id) {
		return makeRepository.findOne(id);
	}

	@Override
	public Make create(Make m) {
		if (m.getID() != null) {
			return null;
		}
		return makeRepository.save(m);
	}

	@Override
	public Make update(Make m) {
		Make orig = makeRepository.findOne(m.getID());
		if (orig == null) {
			return null;
		}
		return makeRepository.save(m);
	}

	@Override
	public void delete(Long id) {
		makeRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return makeRepository.count();
	}

	public Make findByLongName(String str) {
		List<Make> makeList = (List<Make>) findAll();
		Make make = makeList.stream().filter(m -> m.getLongName().equals(str.toUpperCase())).findAny().orElse(null);
		return make;
	}

	public Make findByShortName(String str) {
		List<Make> makeList = (List<Make>) findAll();
		Make make = makeList.stream().filter(m -> m.getShortName().equals(str.toUpperCase())).findAny().orElse(null);
		return make;
	}

}
