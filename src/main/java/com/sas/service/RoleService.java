package com.sas.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Role;
import com.sas.entity.Users;
import com.sas.repository.RoleRepository;
import com.sas.service_interface.RoleServiceInterface;

@Service
public class RoleService implements RoleServiceInterface {
	@Autowired
	RoleRepository roleRepository;

	public Role addUserRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Collection<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Collection<Role> findAll(int pageNumber, int pageSize) {
		return roleRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Role findOne(Long id) {
		return roleRepository.findOne(id);
	}

	@Override
	public Role create(Role role) {
		if (role.getID() != null) {
			return null;
		}
		return roleRepository.save(role);
	}

	@Override
	public Role update(Role role) {
		Role originalUserRole = roleRepository.findOne(role.getID());
		if (originalUserRole == null) {
			return null;
		}
		originalUserRole.setRoleDesc(role.getRoleDesc());
		List<Users> list = role.getUserList();
		originalUserRole.setUserList(list);
		return roleRepository.save(role);
	}

	@Override
	public void delete(Long id) {
		roleRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return roleRepository.count();
	}
}
