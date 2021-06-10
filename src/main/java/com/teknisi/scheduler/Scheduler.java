package com.teknisi.scheduler;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.teknisi.model.AppUser;
import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;
import com.teknisi.services.AppUserService;
import com.teknisi.services.FileService;
import com.teknisi.services.MessageService;
import com.teknisi.services.RequestService;
import com.teknisi.services.TeknisiService;

@Component
public class Scheduler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@Autowired Environment environment;
	@Autowired RequestService requestService;
	@Autowired TeknisiService teknisiService;
	@Autowired MessageService messageService;
	@Autowired AppUserService appUserService;
	@Autowired FileService fileService;
	
	@Scheduled(cron = "0 0/10 * * * *")
	public void sendEmailRequestStatusNew() {
		List<Request> listRequest = requestService.getAllStatusRequest("NEW");
		for (Request request : listRequest) {
			String message = environment.getProperty("mail.template.message");
			String formattedMessage = MessageFormat.format(message, request.getRequest_id(), 
					request.getMerchant_name(), request.getAddress(), request.getCity(), 
					request.getPic());
			logger.debug("Formatted Message {}" + formattedMessage);
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(request.getTeknisi_id());
			for(Teknisi teknisi : listTeknisi) {
				messageService.sendEmail2(teknisi.getEmail(), teknisi.getName(), formattedMessage,  ", You have new request");
				logger.debug("Send reminder ticket request to each Teknisi {} " + teknisi.getEmail() + teknisi.getName());
				requestService.updateRequestMailSent(request);
				logger.info("The request status has been updated to status mail_sent Successsfully");
				logger.debug("Request {}" + request);
			}
		}
		logger.info("Schedule reminder for request status = NEW has been sent to email => " + dateFormat.format(new Date()));
	}
	
	@Scheduled(fixedRate = 300000)
	public void sendEmailRequestStatusMailSent() throws ParseException, java.text.ParseException {
		logger.info("Check all request that has status mail_sent");
		List<Request> listRequest = requestService.getRequestByBeforeDate("MAIL_SENT");
		for (Request request : listRequest) {
			String message = environment.getProperty("mail.template.message");
			String formattedMessage = MessageFormat.format(message, request.getRequest_id(), 
					request.getMerchant_name(), request.getAddress(), request.getCity(), 
					request.getPic());
			logger.debug("Formatted Message {}" + formattedMessage);
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(request.getTeknisi_id());
			for(Teknisi teknisi : listTeknisi) {
				messageService.sendEmail2(teknisi.getEmail(),teknisi.getName(), formattedMessage, ", Please processnew request");
				logger.debug("Send reminder ticket request to each Teknisi {} Successsfully" + teknisi.getEmail() + teknisi.getName());
			}
		}
		logger.info("Schedule reminder for request status = MAIL_SENT has been sent to email => " + dateFormat.format(new Date()));
	}
	

	@Scheduled(cron = "0 0 12 * * 1-5")
	public void emailAllPendingStatus() throws IOException, MessagingException {
		logger.info("Check all request that has status MAIL_SENT, NEW and PROSSESED");
		fileService.exportToCSV();
		logger.info("Exporting all data to CSV");
		logger.info("Get latest CSV that will be send to Admin");
		List<AppUser> listAppUser = appUserService.showAllAppUserRole("ADMIN");
		for (AppUser appUser : listAppUser) {
			String message = environment.getProperty("mail.admin.template.message");
			String formattedMessage = MessageFormat.format(message, appUser.getUsername());
			logger.debug("Formatted Message {}" + formattedMessage);
			messageService.sendEmailRequestWithAttachment( appUser.getEmail(), appUser.getUsername(), ", Here Are The List Pending Request", formattedMessage);
		}
		logger.info("Schedule information for pending request has been sent to admin email");
	}
	
}
