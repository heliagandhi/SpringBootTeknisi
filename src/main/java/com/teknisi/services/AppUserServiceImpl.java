package com.teknisi.services;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;

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
	public void sendEmail(AppUser appUser) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        String message = "<table width=\"100%\"> \r\n"
        		+ "<tr> \r\n"
        		+ " <td align=\"center\"><img src=\"https://llndcz.stripocdn.email/content/guids/CABINET_2663efe83689b9bda1312f85374f56d2/images/10381620386430630.png\" width=\"100\"></td> \r\n"
        		+ "</tr> \r\n"
        		+ "<tr> \r\n"
        		+ " <td align=\"center\"><h2 style=\"Margin:0;line-height:43px;mso-line-height-rule:exactly;font-family:Montserrat, sans-serif;font-size:36px;font-style:normal;font-weight:normal;color:#333333\">Thank you " + appUser.getUsername() + "!! <br /> you have successfully registered</h2></td> \r\n"
        		+ "</tr> \r\n"
        		+ "<tr> \r\n"
        		+ "  <td align=\"center\"> \r\n"
        		+ " <table border=\"0\" width=\"40%\" height=\"100%\" style=\"border-spacing:0px;width:40% !important;display:inline-table\"> \r\n"
        		+ "  <tr> \r\n"
        		+ "   <td style=\"padding:1;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:2px;width:100%;margin:0px\"></td> \r\n"
        		+ "  </tr> \r\n"
        		+ " </table></td> \r\n"
        		+ "</tr> \r\n"
        		+ "</table>";
        helper.setTo(appUser.getEmail());
        helper.setSubject("Testing from Spring Boot Teknisi");
        helper.setText(message, true);
        javaMailSender.send(msg);
    }

}
