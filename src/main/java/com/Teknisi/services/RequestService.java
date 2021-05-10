package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.Request;

public interface RequestService {
	List<Request> showAllRequest();
	void insert(Request request);
	void deleteById(String request_id);
	void updateRequest(Request request);
	Request getRequestById(String request_id);
}
