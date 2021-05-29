package com.Teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.Teknisi.model.Request;
import com.Teknisi.services.RequestService;
import com.Teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/request", tags = "Request Profile Controller")
@RestController
public class RequestController {

	@Autowired RequestService requestService;
	@Autowired TeknisiService teknisiService;

	@ApiOperation(value = "View all request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Iterable.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveAll(Authentication authentication) {
		List<Request> listRequest = requestService.showAllRequest();
		System.out.println(authentication.getAuthorities());
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}

	@ApiOperation(value = "View request by ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Request.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/{request_id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveById(@PathVariable("request_id") String request_id) {
		if (requestService.RequestIdExists(request_id) == true) {
			Request request = requestService.getRequestById(request_id);
			return new ResponseEntity<>(request, HttpStatus.OK);
		} else if (requestService.RequestIdExists(request_id) == false) {
			return new ResponseEntity<>("Request ID did not exist", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Request ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Create an request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/request/create", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> createRequest(@Valid @RequestBody Request request) {
		String request_id = request.getRequest_id();
		long teknisi_id = request.getTeknisi_id();
		if (requestService.RequestIdExists(request_id) != true && teknisiService.TeknisiIdExists(teknisi_id) == true
				&& request.getRequest_id() != null) {
			requestService.insert(request);
			return new ResponseEntity<>("Request Created Successsfully", HttpStatus.OK);
		} else {
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
	public ResponseEntity<Object> updateRequest(@Valid @RequestBody Request request) {
		String request_id = request.getRequest_id();
		long teknisi_id = request.getTeknisi_id();
		if (requestService.RequestIdExists(request_id) == true && teknisiService.TeknisiIdExists(teknisi_id) == true
				&& request.getRequest_id() != null) {
			requestService.updateRequest(request);
			return new ResponseEntity<>("Request Updated Successsfully", HttpStatus.OK);
		} else {
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