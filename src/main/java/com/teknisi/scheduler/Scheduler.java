package com.teknisi.scheduler;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	@Scheduled(cron = "0 0/10 * * * *")
	public ResponseEntity<Object> sendEmailNewRequest() {
		List<Request> listRequest = requestService.getAllStatusNewRequest("NEW");
		for (Request request : listRequest) {
			Long teknisi_id = request.getTeknisi_id();
			String request_id = request.getRequest_id();
			String merchant_name = request.getMerchant_name();
			String address = request.getAddress();
			String city = request.getCity();
			String pic = request.getPic();
			String message = environment.getProperty("mail.template.message");
			String formattedMessage = MessageFormat.format(message, request_id, merchant_name, address, city, pic);
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(teknisi_id);
			for(Teknisi teknisi : listTeknisi) {
				messageService.sendEmail2(teknisi.getEmail(), teknisi.getName(), formattedMessage,  "You have new request");
				requestService.updateRequestMailSent(request);
			}
		}
		logger.info("Send all new request to each Teknisi => " + dateFormat.format(new Date()));
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	
	@Scheduled(fixedRate = 300000)
	public ResponseEntity<Object> sendEmailMailRequest() throws ParseException {
		List<Request> listRequest = requestService.getAllStatusNewRequest("MAIL_SENT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		Calendar calender = Calendar.getInstance();
		calender.setTime(now);
		calender.add(Calendar.HOUR, -6);
		Date now2 = calender.getTime();
		for (Request request : listRequest) {
			Date createdDate = sdf.parse(request.getCreated_date().toString());
			logger.info("cek => {} " + createdDate);
			if(createdDate.before(now2)) {
					Long teknisi_id = request.getTeknisi_id();
					String request_id = request.getRequest_id();
					String merchant_name = request.getMerchant_name();
					String address = request.getAddress();
					String city = request.getCity();
					String pic = request.getPic();
					String message = environment.getProperty("mail.template.message");
					String formattedMessage = MessageFormat.format(message, request_id, merchant_name, address, city, pic);
					List<Teknisi> listTeknisi = teknisiService.getTeknisiById(teknisi_id);
					for(Teknisi teknisi : listTeknisi) {
						messageService.sendEmail2(teknisi.getEmail(),teknisi.getName(), formattedMessage, "Please processnew request");
					}
			}
			logger.info("Send all new request to each Teknisi => " + dateFormat.format(new Date()));
		}
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
}
