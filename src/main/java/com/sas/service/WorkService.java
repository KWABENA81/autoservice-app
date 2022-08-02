package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Work;
import com.sas.repository.WorkRepository;
import com.sas.service_interface.WorkServiceInterface;

@Service
public class WorkService implements WorkServiceInterface {
	@Autowired
	WorkRepository workRepository;

	@Override
	public Collection<Work> findAll() {
		return workRepository.findAll();
	}

	@Override
	public Collection<Work> findAll(int pageNumber, int pageSize) {
		return workRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Work findById(Long id) {
		return workRepository.findOne(id);
	}

	@Override
	public Work create(Work w) {
		if (w.getID() != null) {
			return null;
		}
		return workRepository.save(w);
	}

	@Override
	public Work update(Work work) {
		Work dbWork = workRepository.findOne(work.getID());
		if (dbWork == null) {
			return null;
		}
		dbWork.setDuration(work.getDuration());
		dbWork.setWorkDesc(work.getWorkDesc());
		dbWork.setParts(work.getParts());
		dbWork.setWorkCost(work.getWorkCost());
		//
		return workRepository.save(dbWork);
	}

	@Override
	public void delete(Long id) {
		workRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return workRepository.count();
	}

}
