package com.teknisi.services;

import java.io.IOException;

import javax.mail.MessagingException;

import com.teknisi.model.AppUser;

public interface MessageService {
	void sendEmail(AppUser appUser)throws MessagingException, IOException;
	void sendEmail2(String email, String name, String message, String subject);
	void sendEmailRequestWithAttachment(String email, String name, String Subject, String message, String filePath) throws MessagingException, IOException;
	void sendEmailRecapRequestWithAttachment(String email, String name, String Subject, String message, String filePath) throws MessagingException, IOException;
}
