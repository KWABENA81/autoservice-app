package com.sas.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sas.entity.Users;
import com.sas.repository.RoleRepository;
import com.sas.repository.UsersRepository;
import com.sas.service_interface.UsersServiceInterface;

@Service
public class UsersService implements UsersServiceInterface {
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Users addPersonnel(Users users) {
		return usersRepository.save(users);
	}

	@Override
	public Users findOne(Long id) {
		return usersRepository.findOne(id);
	}

	@Override
	public Users create(Users users) {
		if (users.getID() != null) {
			return null;
		}
		users.setRolesList(new ArrayList<>(roleRepository.findAll()));
		users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
		return usersRepository.save(users);
	}

	@Override
	public Users update(Users users) {
		Users storedUser = usersRepository.findOne(users.getID());
		if (storedUser == null) {
			return null;
		}
		storedUser.setAddress(users.getAddress());
		storedUser.setEmail(users.getEmail());
		storedUser.setPhoneMain(users.getPhoneMain());
		storedUser.setPhoneAux(users.getPhoneAux());
		storedUser.setPassword(users.getPassword());
		storedUser.setStatus(users.getStatus());
		//
		return usersRepository.save(storedUser);
	}

	@Override
	public void delete(Long id) {
		usersRepository.delete(id);
	}

	@Override
	public Collection<Users> findAll() {
		return usersRepository.findAll();
	}

	@Override
	public Collection<Users> findAll(int pageNumber, int pageSize) {
		return usersRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Long getCount() {
		return usersRepository.count();
	}

	public Users findByUserId(String username) {
		return usersRepository.findByUsername(username);
	}

}
