package com.teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.teknisi.model.AppUser;
import com.teknisi.model.Teknisi;
import com.teknisi.services.AppUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class AppUserController {
	
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
		List<AppUser> listAppUser = appUserService.showAllAppUser();
		return new ResponseEntity<>(listAppUser, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View AppUser by ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK", response = Teknisi.class),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/AppUser/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveById(@PathVariable("id") Long id) {
		if(appUserService.AppUserIdExists(id) == true) {
			AppUser appUser = appUserService.getAppUserById(id);
			return new ResponseEntity<>(appUser, HttpStatus.OK);
		}else if (appUserService.AppUserIdExists(id) == false ) {
			return new ResponseEntity<>("AppUser ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
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
	public ResponseEntity<Object> createAppUser(@Valid @RequestBody AppUser appUser) {
		Long id = appUser.getId();
		if(appUserService.AppUserIdExists(id) == true) {
			return new ResponseEntity<>("AppUser ID already exist", HttpStatus.BAD_REQUEST);
		}else if (appUser.getId() == null ) {
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			appUserService.insert(appUser);
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
	public ResponseEntity<Object> updateTeknisi(@Valid @RequestBody AppUser appUser) {
		Long id = appUser.getId();
		if(appUserService.AppUserIdExists(id) == true) {
			appUserService.update(appUser);
			return new ResponseEntity<>("AppUser Updated Successsfully", HttpStatus.OK);
		}else if (appUser.getId() == null ) {
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
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
