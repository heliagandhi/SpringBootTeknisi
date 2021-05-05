package com.Teknisi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value = DataNotfoundException.class)
	public ResponseEntity<Object> dataNotfoundException(DataNotfoundException exception) {
		return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<Object> accessDeniedException(AccessDeniedException exception) {
		return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
	}
}
