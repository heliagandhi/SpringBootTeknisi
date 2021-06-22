package com.teknisi.services;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.teknisi.model.AppUser;

@Service
public class MessageServiceImpl implements MessageService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private JavaMailSender javaMailSender;
//	@Autowired private FileService fileService;
	@Autowired private SpringTemplateEngine templateEngine;
	
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
	public void sendEmailRequestWithAttachment(String email, String name, String subject, String templateName,
			String fileName, byte[] attachment) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        
        Context context = new Context();
        logger.info("setVariable for html");
        context.setVariable("name", name);
        logger.info("Check template");
        String html = templateEngine.process(templateName, context);
        logger.info("Making the email");
        helper.setTo(email);
        helper.setSubject(name+subject);
        helper.setText(html, true);
        helper.addAttachment(fileName, new ByteArrayResource(attachment));
        javaMailSender.send(msg);
	}
}
