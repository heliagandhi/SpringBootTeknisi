package com.teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknisi.model.Teknisi;
import com.teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TeknisiController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired TeknisiService teknisiService;
	
	@ApiOperation(value = "View all teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Iterable.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/teknisi", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveAll() {
		logger.info("Retrieve all teknisi");
		List<Teknisi> listTeknisi = teknisiService.showAllTeknisi();
		logger.info("all teknisi {}", listTeknisi);
		return new ResponseEntity<>(listTeknisi, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View teknisi by ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Teknisi.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/teknisi/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveById(@PathVariable("id") Long id) {
		if(teknisiService.TeknisiIdExists(id) == true) {
			logger.info("Retrieve teknisi by id");
			logger.debug("id : {}", id);
			logger.info("Retrieve teknisi by id : {}", Long.toString(id));
			List<Teknisi> teknisi = teknisiService.getTeknisiById(id);
			logger.info("by id {}", teknisi);
			return new ResponseEntity<>(teknisi, HttpStatus.OK);
		}else if (teknisiService.TeknisiIdExists(id) == false ) {
			logger.debug("Teknisi with id {} not exist", id);
			logger.info("Teknisi with id {} not exist", Long.toString(id));
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Create an teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!")
	})
	@RequestMapping(value = "/teknisi/create", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> createTeknisi(@Valid @RequestBody Teknisi teknisi) throws Exception{
		logger.info("Create teknisi");
		ObjectMapper objectMapper = new ObjectMapper();
		logger.debug("Input {}", objectMapper.writeValueAsString(teknisi));
		logger.info("Input {}", objectMapper.writeValueAsString(teknisi));
		
		Long id = teknisi.getId();
		logger.info(teknisi.getId().toString());
		if(teknisiService.TeknisiIdExists(id) == true) {
			logger.debug("Teknisi with id {} already exist", id);
			return new ResponseEntity<>("Teknisi ID already exist", HttpStatus.BAD_REQUEST);
		}else if (teknisi.getId() == null ) {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			teknisiService.insert(teknisi);
			logger.info("Teknisi Created Successsfully");
			return new ResponseEntity<>("Teknisi Created Successsfully", HttpStatus.OK);
		}
	}
		
	@ApiOperation(value = "Update an teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!")
	})
	@RequestMapping(value = "/teknisi/update", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateTeknisi(@Valid @RequestBody Teknisi teknisi) throws Exception{
		logger.info("Update teknisi");
		ObjectMapper objectMapper = new ObjectMapper();
		logger.debug("Input {}", objectMapper.writeValueAsString(teknisi));
		logger.info("Input {}", objectMapper.writeValueAsString(teknisi));
		
		Long id = teknisi.getId();
		if(teknisiService.TeknisiIdExists(id) == true) {
			teknisiService.updateTeknisi(teknisi);
			logger.info("Teknisi Updated Successsfully");
			return new ResponseEntity<>("Teknisi Updated Successsfully", HttpStatus.OK);
		}else if (teknisi.getId() == null ) {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			logger.debug("Teknisi with id {} already exist", id);
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Delete an teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/teknisi/delete/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		teknisiService.deleteById(id);
		logger.info("Teknisi deleted successsfully");
		logger.info("delete id : {}", Long.toString(id));
		return new ResponseEntity<>("Teknisi deleted successsfully", HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}