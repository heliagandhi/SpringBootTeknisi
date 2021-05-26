package com.Teknisi.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Teknisi.dao.AppUserDao;
import com.Teknisi.model.AppUser;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired AppUserDao appUserDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserDao.findAppUserByUsername(username);
		if (appUserDao.AppUserUsernameExists(username) == true) {
			return new User(appUser.getUsername(), appUser.getPassword(),new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}