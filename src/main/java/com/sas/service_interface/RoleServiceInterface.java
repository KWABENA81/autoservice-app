package com.sas.service_interface;

import java.util.Collection;

import com.sas.entity.Role;

public interface RoleServiceInterface {
	Collection<Role> findAll();

	Collection<Role> findAll(int pageNumber, int pageSize);

	Role findOne(Long id);

	Role create(Role address);

	Role update(Role address);

	void delete(Long id);

	Long getCount();
}
