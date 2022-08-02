package com.sas.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Country;
import com.sas.entity.Region;
import com.sas.repository.RegionRepository;
import com.sas.service_interface.RegionServiceInterface;

@Service
public class RegionService implements RegionServiceInterface {
	@Autowired
	RegionRepository regionRepository;

	@Override
	public Collection<Region> findAll() {
		return regionRepository.findAll();
	}

	@Override
	public Collection<Region> findAll(int pageNumber, int pageSize) {
		return regionRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Region findById(Long id) {
		return regionRepository.findOne(id);
	}

	@Override
	public Region create(Region reg) {
		if (reg.getID() != null) {
			return null;
		}
		return regionRepository.save(reg);
	}

	@Override
	public Region update(Region r) {
		Region origRegion = regionRepository.findOne(r.getID());
		if (origRegion == null) {
			return null;
		}
		return regionRepository.save(r);
	}

	@Override
	public void delete(Long id) {
		regionRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return regionRepository.count();
	}

	@Override
	public List<Region> findByCountry(Country country) {
		return regionRepository.findByCountry(country);
	}

	/**
	 * 
	 * @param reg
	 * @return
	 */
	public Region findByName(String reg) {
		return regionRepository.findByName(reg);
	}
}
