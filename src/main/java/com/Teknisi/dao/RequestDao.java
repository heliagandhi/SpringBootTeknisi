package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.Request;

public interface RequestDao {
	List<Request> getAllRequest();
	void insert(Request request);
	int deleteById(String request_id);
	void updateRequest(Request request);
	public Request findRequestById(String request_id);
}