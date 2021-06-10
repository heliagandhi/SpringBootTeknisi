package com.teknisi.dao;

import java.util.List;

import com.teknisi.model.Request;

public interface RequestDao {
	List<Request> getAllRequest();
	List<Request> getAllPendingRequest();
	List<Request> getAllStatusRequest(String status);
	List<Request> getRequestByBeforeDate(String status);
	void insert(Request request);
	int deleteById(String request_id);
	void updateRequest(Request request);
	void updateRequestMailSent(Request request);
	public Request findRequestById(String request_id);
	boolean RequestIdExists(String request_id);
}