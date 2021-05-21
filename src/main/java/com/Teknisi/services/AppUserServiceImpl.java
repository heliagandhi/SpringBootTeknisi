package com.Teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teknisi.dao.AppUserDao;
import com.Teknisi.model.AppUser;

@Service
public class AppUserServiceImpl implements AppUserService{
	
	@Autowired AppUserDao appUserDao;

	@Override
	public List<AppUser> showAllAppUser() {
		return appUserDao.getAllAppUser();
	}

	@Override
	public AppUser getAppUserById(Long id) {
		return appUserDao.findAppUserById(id);
	}

	@Override
	public void insert(AppUser appUser) {
		appUserDao.insert(appUser);
	}

	@Override
	public void update(AppUser appUser) {
		appUserDao.update(appUser);
	}

	@Override
	public void deleteById(Long id) {
		appUserDao.deleteById(id);
	}

	@Override
	public boolean AppUserIdExists(Long id) {
		return appUserDao.AppUserIdExists(id);
	}

}
