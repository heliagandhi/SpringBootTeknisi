package com.Teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.Teknisi.model.Teknisi;
import com.Teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TeknisiController {
	
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
		List<Teknisi> listTeknisi = teknisiService.showAllTeknisi();
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
			List<Teknisi> teknisi = teknisiService.getTeknisiById(id);
			return new ResponseEntity<>(teknisi, HttpStatus.OK);
		}else if (teknisiService.TeknisiIdExists(id) == false ) {
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//post
	@ApiOperation(value = "Create an teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!")
	})
	@RequestMapping(value = "/teknisi/create", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> createTeknisi(@Valid @RequestBody Teknisi teknisi) {
		Long id = teknisi.getId();
		if(teknisiService.TeknisiIdExists(id) == true) {
			return new ResponseEntity<>("Teknisi ID already exist", HttpStatus.BAD_REQUEST);
		}else if (teknisi.getId() == null ) {
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			teknisiService.insert(teknisi);
			return new ResponseEntity<>("Teknisi Created Successsfully", HttpStatus.OK);
		}
	}
		
		
	//put
	@ApiOperation(value = "Update an teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!")
	})
	@RequestMapping(value = "/teknisi/update", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateTeknisi(@Valid @RequestBody Teknisi teknisi) {
		Long id = teknisi.getId();
		if(teknisiService.TeknisiIdExists(id) == true) {
			teknisiService.updateTeknisi(teknisi);
			return new ResponseEntity<>("Teknisi Updated Successsfully", HttpStatus.OK);
		}else if (teknisi.getId() == null ) {
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//delete
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