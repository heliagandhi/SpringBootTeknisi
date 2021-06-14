package com.teknisi.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.teknisi.model.AppUser;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired private JavaMailSender javaMailSender;
	@Autowired private FileService fileService;
	
	@Value("${template.mail.subject}")
	private String subject;
	
	@Value("${template.mail.message}")
	private String message;
	

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
	public void sendEmail2(String email, String name, String message, String subject) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(name+subject);
        msg.setText(message);
        javaMailSender.send(msg);
	}

	@Override
	public void sendEmailRequestWithAttachment(String email, String name, String Subject, String message, String filePath)
			throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);
        helper.setSubject(name+subject);
        helper.setText(message, true);
        FileSystemResource file = new FileSystemResource(fileService.getLastModified(filePath));
        helper.addAttachment(file.getFilename(), file);
        javaMailSender.send(msg);
		
	}
	
	@Override
	public void sendEmailRecapRequestWithAttachment(String email, String name, String subject, String message, String filePath)
			throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);
        helper.setSubject(name+subject);
        helper.setText(message, true);
        FileSystemResource file = new FileSystemResource(fileService.getLastModified(filePath));
        helper.addAttachment(file.getFilename(), file);
        javaMailSender.send(msg);

    }

}
