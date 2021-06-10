package com.teknisi.services;

import java.util.List;

import com.teknisi.model.Request;

public interface RequestService {
	List<Request> showAllRequest();
	List<Request> showAllPendingRequest();
	List<Request> getAllStatusRequest(String status);
	List<Request> getRequestByBeforeDate(String status);
	void insert(Request request);
	void deleteById(String request_id);
	void updateRequest(Request request);
	void updateRequestMailSent(Request request);
	Request getRequestById(String request_id);
	boolean RequestIdExists(String request_id);
	
}
