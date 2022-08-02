package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.DriveType;
import com.sas.repository.DriveTypeRepository;
import com.sas.service_interface.DriveTypeServiceInterface;

@Service
public class DriveTypeService implements DriveTypeServiceInterface {
	@Autowired
	DriveTypeRepository driveTypeRepository;

	@Override
	public Collection<DriveType> findAll() {
		return driveTypeRepository.findAll();
	}

	@Override
	public Collection<DriveType> findAll(int pageNumber, int pageSize) {
		return driveTypeRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public DriveType findById(Long id) {
		return driveTypeRepository.findOne(id);
	}

	@Override
	public DriveType create(DriveType bt) {
		if (bt.getID() != null) {
			return null;
		}
		return driveTypeRepository.save(bt);
	}

	@Override
	public DriveType update(DriveType dType) {
		DriveType orig = driveTypeRepository.findOne(dType.getID());
		if (orig == null) {
			return null;
		}
		orig.setDType(dType.getDType());
		return driveTypeRepository.save(dType);
	}

	@Override
	public void delete(Long id) {
		driveTypeRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return driveTypeRepository.count();
	}

}
