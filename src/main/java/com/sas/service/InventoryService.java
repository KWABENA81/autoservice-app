package com.sas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Inventory;
import com.sas.repository.InventoryRepository;
import com.sas.service_interface.InventoryServiceInterface;

@Service
public class InventoryService implements InventoryServiceInterface {
	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public List<Inventory> findAll() {
		return inventoryRepository.findAll();
	}

	@Override
	public List<Inventory> findAll(int pageNumber, int pageSize) {
		return inventoryRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Inventory findById(Long id) {
		return inventoryRepository.findOne(id);
	}

	@Override
	public Inventory create(Inventory inv) {
		if (inv.getID() != null) {
			return null;
		}
		return inventoryRepository.save(inv);
	}

	@Override
	public Inventory update(Inventory obj) {
		Inventory storedObj = inventoryRepository.findOne(obj.getID());
		if (storedObj == null) {
			return null;
		}
		storedObj.setCategory(obj.getCategory());
		storedObj.setCostPrice(obj.getCostPrice());
		storedObj.setPartNumber(obj.getPartNumber());
		storedObj.setQuantity(obj.getQuantity());
		storedObj.setQuantityAdj(obj.getQuantityAdj());
		storedObj.setReDistance(obj.getReDistance());
		storedObj.setReference(obj.getReference());
		storedObj.setMsrUnit(obj.getMsrUnit());
		//
		return inventoryRepository.save(storedObj);
	}

	@Override
	public void delete(Long id) {
		inventoryRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return inventoryRepository.count();
	}

	/**
	 * 
	 * @param partNr
	 * @return
	 */
	public Inventory findByPartNr(String partNr) {
		return inventoryRepository.findByPartNr(partNr);
	}

}
