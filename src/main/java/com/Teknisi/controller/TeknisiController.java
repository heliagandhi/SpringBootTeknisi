package com.Teknisi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Teknisi.exception.DataNotfoundException;
import com.Teknisi.model.Teknisi;
import com.Teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TeknisiController {
	private Logger logger = LoggerFactory.getLogger("SpringBootTeknisiApplication");
	
	@Autowired TeknisiService teknisiService;
	
	@ApiOperation(value = "View all teknisi", response = Iterable.class, tags = "teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	
	@RequestMapping(value = "/teknisi", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAll() {
		List<Teknisi> listTeknisi = teknisiService.showAllTeknisi();
		return new ResponseEntity<>(listTeknisi, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View teknisi by ID", response = Teknisi.class, tags = "teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/teknisi/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveById(@PathVariable("id") Long id) {
		logger.debug("Get with id : " + id);
		if(id.equals(null)) throw new DataNotfoundException();
		Teknisi teknisi = teknisiService.getTeknisiById(id);
		return new ResponseEntity<>(teknisi, HttpStatus.OK);
	}
	
	
	//delete
	@ApiOperation(value = "Delete an teknisi", response = Teknisi.class, tags = "teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/teknisi/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		teknisiService.deleteById(id);
		return new ResponseEntity<>(teknisiService, HttpStatus.OK);
	}
	
	
	//post
	@ApiOperation(value = "Create an teknisi", response = Teknisi.class, tags = "teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/teknis/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createTeknisi(@RequestBody Teknisi teknisi) {
		teknisiService.insert(teknisi);
		return new ResponseEntity<>(teknisiService, HttpStatus.OK);
	}
	
	
	//put
	@ApiOperation(value = "Update an teknisi", response = Teknisi.class, tags = "teknisi")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/teknis/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateTeknisi(@RequestBody Teknisi teknisi) {
		teknisiService.updateTeknisi(teknisi);
		return new ResponseEntity<>(teknisiService, HttpStatus.OK);
	}
	
	
	
}

