package com.teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teknisi.dao.AppUserDao;
import com.teknisi.model.AppUser;

@Service
public class AppUserServiceImpl implements AppUserService{
	
	@Autowired AppUserDao appUserDao;
	@Autowired private PasswordEncoder bcryptEncoder;
	@Autowired private JavaMailSender javaMailSender;

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
		appUser.setUsername(appUser.getUsername());
		appUser.setPassword(bcryptEncoder.encode(appUser.getPassword()));
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

	@Override
	public boolean AppUserUsernameExists(String username) {
		return appUserDao.AppUserUsernameExists(username);
	}

	@Override
	public void sendEmail(AppUser appUser) {
		SimpleMailMessage msg = new SimpleMailMessage();
		String message = "Hello "+ appUser.getUsername();
        msg.setTo(appUser.getEmail());
        msg.setSubject("Testing from Spring Boot Teknisi");
        msg.setText(message);
        javaMailSender.send(msg);
	}

}
