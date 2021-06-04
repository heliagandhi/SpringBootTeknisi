package com.teknisi.services;

import java.util.List;

import com.teknisi.model.AppUser;

public interface AppUserService {
	List<AppUser> showAllAppUser();
	AppUser getAppUserById(Long id);
	void insert(AppUser appUser);
	void update(AppUser appUser);
	void deleteById(Long id);
	boolean AppUserIdExists(Long id);
	boolean AppUserUsernameExists(String username);
	void sendEmail(AppUser appUser);
}
