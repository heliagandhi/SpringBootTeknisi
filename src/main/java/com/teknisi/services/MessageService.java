package com.teknisi.services;

import java.io.IOException;

import javax.mail.MessagingException;

import com.teknisi.model.AppUser;
import com.teknisi.model.Request;

public interface MessageService {
	void sendEmail(AppUser appUser)throws MessagingException, IOException;
	void sendEmail2(String email, String username, Request request, String subject);
}
