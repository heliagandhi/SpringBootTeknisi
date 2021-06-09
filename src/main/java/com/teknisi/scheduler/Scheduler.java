package com.teknisi.scheduler;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;
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
	
//	@Scheduled(cron = "0 0/10 * * * *")
//	@Scheduled(cron = "*/5 * * * * *") 
	public ResponseEntity<Object> sendEmailNewRequest() {
		List<Request> listRequest = requestService.getAllStatusNewRequest("NEW", false);
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
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	
//	@Scheduled(fixedRate = 300000)
//	@Scheduled(fixedRate = 3000)
//	public ResponseEntity<Object> sendEmailMailRequest() throws ParseException, java.text.ParseException {
//		List<Request> listRequest = requestService.getAllStatusNewRequest("MAIL_SENT", true);
//		for (Request request : listRequest) {
//			String message = environment.getProperty("mail.template.message");
//			String formattedMessage = MessageFormat.format(message, request.getRequest_id(), 
//					request.getMerchant_name(), request.getAddress(), request.getCity(), 
//					request.getPic());
//			logger.debug("Formatted Message {}" + formattedMessage);
//			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(request.getTeknisi_id());
//			for(Teknisi teknisi : listTeknisi) {
//				messageService.sendEmail2(teknisi.getEmail(),teknisi.getName(), ", Please processnew request", formattedMessage);
//				logger.debug("Send reminder ticket request to each Teknisi {} Successsfully" + teknisi.getEmail() + teknisi.getName());
//			
//					}
//			}
//			logger.info("Schedule reminder for request status = MAIL_SENT has been sent to email => " + dateFormat.format(new Date()));
//			return new ResponseEntity<>(listRequest, HttpStatus.OK);
//	}
	
	@Scheduled(fixedRate = 3000)
	public ResponseEntity<Object> sendEmailMailRequestV2() throws ParseException, java.text.ParseException {
		logger.info("Check all ticket request that has status mail_sent");
		List<Request> listRequest = requestService.getAllStatusNewRequest("MAIL_SENT", true);
		for (Request request : listRequest) {
			String message = environment.getProperty("mail.template.message");
			String formattedMessage = MessageFormat.format(message, request.getRequest_id(), 
					request.getMerchant_name(), request.getAddress(), request.getCity(), 
					request.getPic());
			logger.debug("Formatted Message {}" + formattedMessage);
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(request.getTeknisi_id());
			for(Teknisi teknisi : listTeknisi) {
				messageService.sendEmail2(teknisi.getEmail(),teknisi.getName(), formattedMessage, ", Please processnew request");
				logger.debug("Send reminder ticket request to each Teknisi {} " + teknisi.getEmail() + teknisi.getName());
			}
		}
		logger.info("Schedule reminder for ticket request status = mail_sent has been sent to email");
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
}
