package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.AppUser;

public interface AppUserServiceInterface {
	Collection<AppUser> findAll();

	Collection<AppUser> findAll(int pageNumber, int pageSize);

	AppUser findById(Long id);

	AppUser findUserByUsername(String uname);

	AppUser create(AppUser user);

	AppUser update(AppUser user);

	void delete(Long id);

	void saveUser(AppUser user);

	Long getCount();
}
