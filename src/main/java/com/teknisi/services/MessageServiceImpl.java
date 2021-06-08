package com.teknisi.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.teknisi.model.AppUser;
import com.teknisi.model.Request;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired private JavaMailSender javaMailSender;
	
	@Value("${template.mail.subject}")
	private String subject;
	
	@Value("${template.mail.message}")
	private String message;
	
//	@Value("${template.mail.subject2:You have new request}")
//	private String subject2;
	
//	@Value("${template.mail.message2}")
//	public Request setRequestData(Request request, String request_id, String merchant_name) {
//		request.setRequest_id(request_id);
//		request.setMerchant_name(merchant_name);
//		return request;
//	}

	@Override
	public void sendEmail(AppUser appUser) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(appUser.getEmail());
        helper.setSubject(subject);
        helper.setText(message, true);
        javaMailSender.send(msg);
    }

	@Override
	public void sendEmail2(String email, String username, Request request, String subject) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText("Request ID : "+request.getRequest_id()+" \r\n"
        		+ "Merchant : "+request.getMerchant_name()+" \r\n"
        		+ "Address : "+request.getAddress()+" \r\n"
        		+ "City : "+request.getCity()+" \r\n"
        		+ "Person in charge : "+request.getPic()+"\r\n"
        		+ "\r\n"
        		+ "Thank you");
        javaMailSender.send(msg);
	}
	
}
