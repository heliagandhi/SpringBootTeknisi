package com.teknisi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknisi.exporter.CsvExporter;
import com.teknisi.model.Request;
import com.teknisi.services.MessageService;
import com.teknisi.services.RequestService;
import com.teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/request", tags = "Request Profile Controller")
@RestController
public class RequestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired RequestService requestService;
	@Autowired TeknisiService teknisiService;
	@Autowired MessageService messageService;
	@Autowired CsvExporter csvExporter;

	@ApiOperation(value = "View all request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Iterable.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveAll(Authentication authentication) {
		logger.info("Retrieve all teknisi");
		List<Request> listRequest = requestService.showAllRequest();
		logger.info("all teknisi {}", listRequest);
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}

	@ApiOperation(value = "View request by ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Request.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/id/{request_id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveById(@PathVariable("request_id") String request_id) {
		if (requestService.RequestIdExists(request_id) == true) {
			logger.info("Retrieve teknisi by id");
			logger.debug("id : {}", request_id);
			Request request = requestService.getRequestById(request_id);
			logger.info("by id {}", request);
			return new ResponseEntity<>(request, HttpStatus.OK);
		} else if (requestService.RequestIdExists(request_id) == false) {
			logger.debug("Request with id {} not exist", request_id);
			return new ResponseEntity<>("Request ID did not exist", HttpStatus.BAD_REQUEST);
		} else {
			logger.error("Request id cannot be empty");
			return new ResponseEntity<>("Request ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "View request by Status")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Request.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/status/{status}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> retrieveByStatus(@PathVariable("status") String status) {
		logger.info("Retrieve teknisi by status");
		logger.debug("id : {}", status);
		List<Request> listRequest = requestService.getAllStatusRequest(status);
		logger.info("by id {}", listRequest);
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}

	@ApiOperation(value = "Create an request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/create", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> createRequest(@Valid @RequestBody Request request) throws Exception{
		logger.info("Create Request");
		ObjectMapper objectMapper = new ObjectMapper();
		logger.debug("Input {}", objectMapper.writeValueAsString(request));
		logger.info("Input {}", objectMapper.writeValueAsString(request));
		String request_id = request.getRequest_id();
		long teknisi_id = request.getTeknisi_id();
		if (requestService.RequestIdExists(request_id) != true && teknisiService.TeknisiIdExists(teknisi_id) == true
				&& request.getRequest_id() != null) {
			requestService.insert(request);
			logger.info("Request Created Successsfully");
			return new ResponseEntity<>("Request Created Successsfully", HttpStatus.OK);
		} else {
			logger.debug("Request with id {} already exist", request_id);
			return new ResponseEntity<>("teknisi_id is not listed on the teknisi table", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Update an request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/update", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateRequest(@Valid @RequestBody Request request) throws Exception{
		logger.info("Update request");
		ObjectMapper objectMapper = new ObjectMapper();
		logger.debug("Input {}", objectMapper.writeValueAsString(request));
		logger.info("Input {}", objectMapper.writeValueAsString(request));
		String request_id = request.getRequest_id();
		long teknisi_id = request.getTeknisi_id();
		if (requestService.RequestIdExists(request_id) == true && teknisiService.TeknisiIdExists(teknisi_id) == true
				&& request.getRequest_id() != null) {
			requestService.updateRequest(request);
			logger.info("Teknisi Updated Successsfully");
			return new ResponseEntity<>("Request Updated Successsfully", HttpStatus.OK);
		} else {
			logger.debug("Teknisi with id {} already exist", request_id);
			return new ResponseEntity<>("teknisi_id is not listed on the teknisi table", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Delete an request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/delete/{request_id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable("request_id") String request_id) {
		requestService.deleteById(request_id);
		logger.info("Teknisi deleted successsfully");
		logger.info("delete id : {}", request_id);
		return new ResponseEntity<>("Request deleted successsfully", HttpStatus.OK);
	}
	

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}