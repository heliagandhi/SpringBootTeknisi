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
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.teknisi.model.AppUser;
import com.teknisi.services.AppUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class AppUserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired AppUserService appUserService;
	
	@ApiOperation(value = "View all AppUser")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Iterable.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	
	@RequestMapping(value = "/AppUser", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveAll() {
		logger.info("Retrieve all AppUser");
		List<AppUser> listAppUser = appUserService.showAllAppUser();
		logger.info("all AppUser {}", listAppUser);
		return new ResponseEntity<>(listAppUser, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View AppUser by ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = AppUser.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/AppUser/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveById(@PathVariable("id") Long id) {
		if(appUserService.AppUserIdExists(id) == true) {
			logger.info("Retrieve AppUser by id");
			logger.debug("id : {}", id);
			logger.info(" id : {}", Long.toString(id));
			AppUser appUser = appUserService.getAppUserById(id);
			logger.info("by id {}", appUser);
			return new ResponseEntity<>(appUser, HttpStatus.OK);
		}else if (appUserService.AppUserIdExists(id) == false ) {
			logger.debug("AppUser with id {} not exist", id);
			return new ResponseEntity<>("AppUser ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("AppUser ID cannot be empty");
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//post
	@ApiOperation(value = "Create an AppUser")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!")
	})
	@RequestMapping(value = "/AppUser/create", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> createAppUser(@Valid @RequestBody AppUser appUser) throws Exception{
		logger.info("Create AppUser");
		ObjectMapper objectMapper = new ObjectMapper();
		logger.debug("Input {}", objectMapper.writeValueAsString(appUser));
		logger.info("Input {}", objectMapper.writeValueAsString(appUser));
		
		Long id = appUser.getId();
		if(appUserService.AppUserIdExists(id) == true) {
			logger.debug("AppUser with ID {} already exist", id);
			return new ResponseEntity<>("AppUser ID already exist", HttpStatus.BAD_REQUEST);
		}else if (appUser.getId() == null ) {
			logger.error("AppUser id cannot be empty");
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			appUserService.insert(appUser);
			logger.info("AppUser Created Successsfully");
			return new ResponseEntity<>("AppUser Created Successsfully", HttpStatus.OK);
		}
	}
		
		
	//put
	@ApiOperation(value = "Update an AppUser")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!")
	})
	@RequestMapping(value = "/AppUser/update", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateAppUser(@Valid @RequestBody AppUser appUser) throws Exception{
		logger.info("Update AppUser");
		ObjectMapper objectMapper = new ObjectMapper();
		logger.debug("Input {}", objectMapper.writeValueAsString(appUser));
		logger.info("Input {}", objectMapper.writeValueAsString(appUser));
		
		Long id = appUser.getId();
		if(appUserService.AppUserIdExists(id) == true) {
			appUserService.update(appUser);
			logger.info("AppUser Updated Successsfully");
			return new ResponseEntity<>("AppUser Updated Successsfully", HttpStatus.OK);
		}else if (appUser.getId() == null ) {
			logger.error("AppUser id cannot be empty");
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			logger.debug("AppUser with id {} already exist", id);
			return new ResponseEntity<>("AppUser ID did not exist", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//delete
	@ApiOperation(value = "Delete an AppUser")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/AppUser/delete/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		appUserService.deleteById(id);
		logger.info("AppUser deleted successsfully");
		logger.info("delete id : {}", Long.toString(id));
		return new ResponseEntity<>("AppUser deleted successsfully", HttpStatus.OK);
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
