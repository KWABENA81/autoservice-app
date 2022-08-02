package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.SStatus;
import com.sas.repository.SStatusRepository;
import com.sas.service_interface.SStatusServiceInterface;

@Service
public class SStatusService implements SStatusServiceInterface {
	@Autowired
	SStatusRepository sstatusRepository;

	@Override
	public Collection<SStatus> findAll() {
		return sstatusRepository.findAll();
	}

	@Override
	public Collection<SStatus> findAll(int pageNumber, int pageSize) {
		return sstatusRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public SStatus findById(Long id) {
		return sstatusRepository.findOne(id);
	}

	@Override
	public SStatus create(SStatus ss) {
		if (ss.getID() != null) {
			return null;
		}
		return sstatusRepository.save(ss);
	}

	@Override
	public SStatus update(SStatus ss) {
		SStatus dbValue = sstatusRepository.findOne(ss.getID());
		if (dbValue == null) {
			return null;
		}
		dbValue.setDescription(ss.getDescription());
		dbValue.setStatus(ss.getStatus());
		//
		return sstatusRepository.save(dbValue);
	}

	@Override
	public void delete(Long id) {
		sstatusRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return sstatusRepository.count();
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	public SStatus findByStatus(String status) {
		return sstatusRepository.findByStatus(status);
	}

}
