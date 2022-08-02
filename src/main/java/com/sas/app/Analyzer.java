package com.sas.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.entity.Address;
import com.sas.repository.AddressRepository;
import com.sas.repository.UsersRepository;
import com.sas.service.AddressService;
import com.sas.service.UsersService;

@Component
public class Analyzer {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	UsersService usersService;
	@Autowired
	AddressService addressService;

	@Autowired
	public Analyzer(AddressRepository repository, AddressService service) {
		this.addressRepository = repository;
		this.addressService = service;

		// test Insert Address
//		Address address = new Address();
//		address.setCity("GTA");
//		address.setCountry("CDA");
//		address.setMailcode("M8U 2Y8");
//		address.setOther("Suite 12");
//		address.setRegion("ONT");
//		address.setStreet("12 hogan dr");
//
//		Address newAddress = addressService.addAddress(address);

		//if (newAddress != null) {
		//	System.out.println("\tDB Address info:26 :" + addressRepository.findAll());
		//}
	}
}
