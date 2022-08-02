package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sas.entity.AppUser;
import com.sas.repository.AppUserRepository;
import com.sas.service_interface.AppUserServiceInterface;

@Service
public class AppUserService implements AppUserServiceInterface {
	@Autowired
	AppUserRepository appUserRepository;

	// @Autowired
	// private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AppUser addAppUser(AppUser user) {
		return appUserRepository.save(user);
	}

	@Override
	public Collection<AppUser> findAll() {
		return appUserRepository.findAll();
	}

	@Override
	public Collection<AppUser> findAll(int pageNumber, int pageSize) {
		return appUserRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public AppUser findById(Long id) {
		return appUserRepository.findOne(id);
	}

	@Override
	public AppUser create(AppUser user) {
		if (user.getID() != null) {
			return null;
		}
		return appUserRepository.save(user);
	}

	@Override
	public AppUser update(AppUser auser) {
		AppUser storedAppUser = appUserRepository.findOne(auser.getID());
		if (storedAppUser == null) {
			return null;
		}
		storedAppUser.setETimestamp(auser.getETimestamp());
		return appUserRepository.save(storedAppUser);
	}

	@Override
	public void delete(Long id) {
		appUserRepository.delete(id);
	}

	//@Override
//	public AppUser findUserByUsername(String uname) {
//		return appUserRepository.findUserByUsername(uname);
//	}

	@Override
	public void saveUser(AppUser user) {
		appUserRepository.save(user);
	}

	@Override
	public Long getCount() {
		return appUserRepository.count();
	}

	@Override
	public AppUser findUserByUsername(String uname) {
		// TODO Auto-generated method stub
		return null;
	}
}
