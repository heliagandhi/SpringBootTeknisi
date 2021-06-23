package com.teknisi.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import com.teknisi.model.Request;

import net.sf.jasperreports.engine.JRException;

public interface RequestService {
	List<Request> showAllRequest();
	List<Request> showAllPendingRequest();
	List<Request> showAllRecapitulationRequest();
	List<Request> getAllStatusRequest(String status);
	List<Request> getRequestByBeforeDate(String status);
	List<Request> showRequestReport(Date start_date, Date end_date);
	void insert(Request request);
	void deleteById(String request_id);
	void updateRequest(Request request);
	void updateRequestMailSent(Request request);
	Request getRequestById(String request_id);
	boolean RequestIdExists(String request_id);
	
}
