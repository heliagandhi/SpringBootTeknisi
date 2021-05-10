package com.Teknisi.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Teknisi.exception.DataNotfoundException;
import com.Teknisi.model.Request;
import com.Teknisi.model.Teknisi;
import com.Teknisi.services.RequestService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class RequestController {
	private Logger logger = LoggerFactory.getLogger("SpringBootTeknisiApplication");
	
	@Autowired RequestService requestService;
	
	
	@ApiOperation(value = "View all request", response = Iterable.class, tags = "request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAll() {
		List<Request> listRequest = requestService.showAllRequest();
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "View request by ID", response = Request.class, tags = "request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveById(@PathVariable("request_id") String request_id) {
		logger.debug("Get with id : " + request_id);
		if(request_id.equals(null)) throw new DataNotfoundException();
		Request request = requestService.getRequestById(request_id);
		return new ResponseEntity<>(request, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Create an request", response = Request.class, tags = "request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createRequest(@Valid @RequestBody Request request, final BindingResult bindingResult) {
		requestService.insert(request);
		return new ResponseEntity<>("request created successsfully", HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Update an request", response = Request.class, tags = "request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateRequest(@Valid @RequestBody Request request, final BindingResult bindingResult) {
		requestService.updateRequest(request);
		return new ResponseEntity<>("Request updated successsfully", HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Delete an request", response = Teknisi.class, tags = "request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/delete/{request_id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("request_id") String request_id) {
		requestService.deleteById(request_id);
		return new ResponseEntity<>("Teknisi deleted successsfully", HttpStatus.OK);
	}
	
}