package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Users;

public interface UsersServiceInterface {
	Collection<Users> findAll();

	Collection<Users> findAll(int pageNumber, int pageSize);

	Users findOne(Long id);

	Users create(Users p);

	Users update(Users p);

	void delete(Long id);

	Long getCount();
}
