package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.AppUser;

public interface AppUserService {
	List<AppUser> showAllAppUser();
	AppUser getAppUserById(Long id);
	void insert(AppUser appUser);
	void update(AppUser appUser);
	void deleteById(Long id);
	boolean AppUserIdExists(Long id);
}
