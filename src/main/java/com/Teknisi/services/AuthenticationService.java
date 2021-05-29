package com.Teknisi.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Teknisi.dao.AppUserDao;
import com.Teknisi.model.AppUser;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired AppUserDao appUserDao;
	
	@Override
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
		AppUser appUser = appUserDao.getUserInfo(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(appUser.getRole());
		UserDetails userDetails = (UserDetails)new User(appUser.getUsername(), 
				appUser.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
} 