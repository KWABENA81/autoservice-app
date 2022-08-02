package com.sas.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Address;
import com.sas.entity.Region;
import com.sas.repository.AddressRepository;
import com.sas.service_interface.AddressServiceInterface;

@Service
public class AddressService implements AddressServiceInterface {
	protected static final String CDA = "([A-Za-z][0-9][A-Za-z]){1}([0-9][A-Za-z][0-9]){1}";
	protected static final String MAILCODE_US = "([A-Za-z][0-9][A-Za-z]){1}([0-9][A-Za-z][0-9]){1}";
	protected static final String MAILCODE_UK = "([A-Za-z][0-9][A-Za-z]){1}([0-9][A-Za-z][0-9]){1}";
	@Autowired
	AddressRepository addressRepository;

	public Address addAddress(Address address) {
		return addressRepository.save(address);
	}

	@Override
	public Collection<Address> findAll() {
		return addressRepository.findAll();
	}

	@Override
	public Collection<Address> findAll(int pageNumber, int pageSize) {
		return addressRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Address findById(Long id) {
		return addressRepository.findOne(id);
	}

	@Override
	public Address create(Address address) {
		if (address.getID() != null) {
			return null;
		}
		return addressRepository.save(address);
	}

	@Override
	public Address update(Address address) {
		Address storedAddress = addressRepository.findOne(address.getID());
		if (storedAddress == null) {
			return null;
		}
		storedAddress.setCity(address.getCity());
		storedAddress.setMailcode(address.getMailcode());
		storedAddress.setOther(address.getOther());
		storedAddress.setRegion(address.getRegion());
		storedAddress.setStreet(address.getStreet());
		//
		return addressRepository.save(storedAddress);
	}

	@Override
	public void delete(Long id) {
		addressRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return addressRepository.count();
	}

	public List<Address> findAddressByRegion(Region region) {
		return addressRepository.findAddressByRegion(region);
	}

}
