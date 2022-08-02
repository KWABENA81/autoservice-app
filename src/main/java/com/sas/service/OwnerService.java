package com.sas.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Address;
import com.sas.entity.Owner;
import com.sas.repository.OwnerRepository;
import com.sas.service_interface.OwnerServiceInterface;

@Service
public class OwnerService implements OwnerServiceInterface {
	@Autowired
	OwnerRepository ownerRepository;

	@Override
	public Collection<Owner> findAll() {
		return ownerRepository.findAll();
	}

	@Override
	public Collection<Owner> findAll(int pageNumber, int pageSize) {
		return ownerRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Owner findById(Long id) {
		return ownerRepository.findOne(id);
	}

	@Override
	public Owner create(Owner own) {
		if (own.getID() != null) {
			return null;
		}
		Address address = own.getAddress();
		address.addVehicleOwner(own);
		return ownerRepository.save(own);
	}

	@Override
	public Owner update(Owner own) {
		Owner storedOwner = ownerRepository.findOne(own.getID());
		if (storedOwner == null) {
			return null;
		}
		storedOwner.setEmail(own.getEmail());
		storedOwner.setPhoneOther(own.getPhoneOther());
		storedOwner.setPhone(own.getPhone());
		storedOwner.setAddress(own.getAddress());
		//
		return ownerRepository.save(storedOwner);
	}

	@Override
	public void delete(Long id) {
		ownerRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return ownerRepository.count();
	}

	/**
	 * 
	 * @param name
	 * @param phone
	 * @return
	 */
	public Owner findByNameAndPhone(String name, String phone) {
		return ownerRepository.findByNameAndPhone(name, name, phone);
	}

	/**
	 * 
	 * @param fname
	 * @param lname
	 * @param phone
	 * @return
	 */
	public Owner findByNamesAndPhone(String fname, String lname, String phone) {
		return ownerRepository.findByNameAndPhone(fname, lname, phone);
	}

	/**
	 * 
	 * @param fname
	 * @param lname
	 * @return
	 */
	public Owner findByNames(String fname, String lname) {
		return ownerRepository.findByNames(fname, lname);
	}

	/**
	 * Searches by Client firstname or lastname
	 * 
	 * @param name
	 * @return
	 */
	public List<Owner> findByName(String name) {
		return ownerRepository.findByName(name, name);
	}

	/**
	 * 
	 * @param phone
	 * @return
	 */
	public List<Owner> findByPhone(String phone) {
		return ownerRepository.findByPhone(phone, phone);
	}

	/**
	 * 
	 * @return
	 */
	public Owner getDefaultOwner() {
		String fname = "ASKSEF", lname = "Auto";
		return ownerRepository.findByNames(fname, lname);
	}

}
