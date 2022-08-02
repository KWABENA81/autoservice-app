package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.UStatus;
import com.sas.repository.UStatusRepository;
import com.sas.service_interface.UStatusServiceInterface;

@Service
public class UStatusService implements UStatusServiceInterface {
	@Autowired
	UStatusRepository uStatusRepository;

	public UStatus addAddress(UStatus address) {
		return uStatusRepository.save(address);
	}

	@Override
	public Collection<UStatus> findAll() {
		return uStatusRepository.findAll();
	}

	@Override
	public Collection<UStatus> findAll(int pageNumber, int pageSize) {
		return uStatusRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public UStatus findOne(Long id) {
		return uStatusRepository.findOne(id);
	}

	@Override
	public UStatus create(UStatus address) {
		if (address.getID() != null) {
			return null;
		}
		return uStatusRepository.save(address);
	}

	@Override
	public UStatus update(UStatus status) {
		UStatus originalStatus = uStatusRepository.findOne(status.getID());
		if (originalStatus == null) {
			return null;
		}
		return uStatusRepository.save(status);
	}

	@Override
	public void delete(Long id) {
		uStatusRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return uStatusRepository.count();
	}

	@Override
	public UStatus findByStatus(String str) {
		return uStatusRepository.findByStatus(str);
	}

}
