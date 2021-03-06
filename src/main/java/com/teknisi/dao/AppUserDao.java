package com.teknisi.dao;

import java.util.List;

import com.teknisi.model.AppUser;

public interface AppUserDao {
	List<AppUser> getAllAppUser();
	List<AppUser> getAllAppUserRole(String role);
	public AppUser findAppUserById(Long id);
	void insert(AppUser AppUser);
	void update(AppUser appUser);
	int deleteById(Long id);
	boolean AppUserIdExists(Long id);
	boolean AppUserUsernameExists(String username);
	public AppUser findAppUserByUsername(String username);
	public AppUser getUserInfo(String username);	
}
