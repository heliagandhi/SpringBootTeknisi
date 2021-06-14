package com.teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teknisi.dao.RequestDao;
import com.teknisi.model.Request;

@Service
public class RequestServiceImpl implements RequestService {
	
@Autowired RequestDao requestDao;
	
	
	@Override
	public List<Request> showAllRequest() {
		return requestDao.getAllRequest();
	}
	
	
	@Override
	public List<Request> showAllPendingRequest() {
		return requestDao.getAllPendingRequest();
	}

	
	@Override
	public void insert(Request request) {
		requestDao.insert(request);	
	}

	
	@Override
	public void deleteById(String request_id) {
		requestDao.deleteById(request_id);	
	}
	

	@Override
	public void updateRequest(Request request) {
		requestDao.updateRequest(request);	
	}
	
	
	@Override
	public void updateRequestMailSent(Request request) {
		requestDao.updateRequestMailSent(request);
		
	}

	
	@Override
	public Request getRequestById(String request_id) {
		return requestDao.findRequestById(request_id);
		
	}


	@Override
	public boolean RequestIdExists(String request_id) {
		return requestDao.RequestIdExists(request_id);
	}


	@Override
	public List<Request> getAllStatusRequest(String status) {
		return requestDao.getAllStatusRequest(status);
	}


	@Override
	public List<Request> getRequestByBeforeDate(String status) {
		return requestDao.getRequestByBeforeDate(status);
	}


	@Override
	public List<Request> showAllRecapitulationRequest() {
		return requestDao.getAllRecapitulationRequest();
	}

}
