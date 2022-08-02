package com.sas.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Role;
import com.sas.entity.Users;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UsersService usersService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = usersService.findByUserId(username);// findByUserId(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : users.getRolesList()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleDesc()));
		}
		return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(),
				grantedAuthorities);
	}
}