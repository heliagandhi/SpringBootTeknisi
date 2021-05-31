package com.Teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Teknisi.model.TeknisiPhoto;
import com.Teknisi.services.TeknisiPhotoService;
import com.Teknisi.services.TeknisiService;
import com.Teknisi.utils.ImagesUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TeknisiPhotoController {

	@Autowired TeknisiPhotoService teknisiPhotoService;
	@Autowired TeknisiService teknisiService;
	@Autowired ImagesUtils imagesUtils;
	
	@ApiOperation(value = "View All TeknisiPhoto Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Suceess|OK", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/showAllTeknisiPhoto", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveAllTeknisiPhoto() {
		List<TeknisiPhoto> listTeknisiPhoto = teknisiPhotoService.showAllTeknisiPhoto();
		return new ResponseEntity<>(listTeknisiPhoto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View TeknisiPhoto by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Suceess|OK", response = TeknisiPhoto.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<Object> retrieveTeknisiPhotoById(@Valid @PathVariable("id") Long id) {
		if(teknisiPhotoService.TeknisiPhotoIdExists(id) == true) {
			TeknisiPhoto teknisiPhoto = teknisiPhotoService.getTeknisiPhotoById(id);
			return new ResponseEntity<>(teknisiPhoto, HttpStatus.OK);
		}else if (teknisiPhotoService.TeknisiPhotoIdExists(id) == false ) {
			return new ResponseEntity<>("Teknisi Photo ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Teknisi Photo ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Create TeknisiPhoto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been created"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> createTeknisiPhoto(@Valid @ModelAttribute TeknisiPhoto teknisiPhoto,  @RequestPart MultipartFile file) throws Exception{
		String fileName = file.getOriginalFilename().split("\\.")[0];
		String fileType = file.getOriginalFilename().split("\\.")[1];
		byte[] fileByte =  file.getBytes();
		String base64 = imagesUtils.convertImagesBase64(fileByte, fileType);
		Long id = teknisiPhoto.getId();
		Long teknisi_id = teknisiPhoto.getTeknisi_id();
		if(teknisiPhotoService.TeknisiPhotoIdOrTeknisiIdExists(id, teknisi_id) != true && teknisiService.TeknisiIdExists(teknisi_id) == true && teknisiPhoto.getId() != null
				&& (fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg") && fileType.isEmpty() != false && fileType.isBlank() != false)) {
			teknisiPhotoService.insertTeknisiPhoto(teknisiPhoto, fileName, fileType, base64);
			return new ResponseEntity<>("TeknisiPhoto Created Successsfully", HttpStatus.OK);
		}else if(teknisiPhotoService.TeknisiPhotoIdOrTeknisiIdExists(id, teknisi_id) == true) {
			return new ResponseEntity<>("TeknisiPhoto ID already exist and Teknisi ID already exist", HttpStatus.BAD_REQUEST);
		}else if(teknisiService.TeknisiIdExists(teknisi_id) != true){
			return new ResponseEntity<>("Teknisi ID is not exist", HttpStatus.BAD_REQUEST);
		}else if (teknisiPhoto.getId() == null || teknisiPhoto.getTeknisi_id() == 0) {
			return new ResponseEntity<>("TeknisiPhoto ID cannot be empty or Teknisi ID cannot be zero", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Update TeknisiPhoto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been updated"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/update", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateTeknisiPhoto(@Valid @ModelAttribute TeknisiPhoto teknisiPhoto,  @RequestPart MultipartFile file) throws Exception{
		String fileName = file.getOriginalFilename().split("\\.")[0];
		String fileType = file.getOriginalFilename().split("\\.")[1];
		byte[] fileByte =  file.getBytes();
		String base64 = imagesUtils.convertImagesBase64(fileByte, fileType);
		Long id = teknisiPhoto.getId();
		Long teknisi_id = teknisiPhoto.getTeknisi_id();
		if(teknisiPhotoService.TeknisiPhotoIdAndTeknisiIdExists(id, teknisi_id) == true && teknisiService.TeknisiIdExists(teknisi_id) == true && teknisiPhoto.getId() != null
				&& (fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg") && fileType.isEmpty() != false && fileType.isBlank() != false)) {
			teknisiPhotoService.updateTeknisiPhoto(teknisiPhoto, fileName, fileType, base64);
			return new ResponseEntity<>("TeknisiPhoto Updated Successsfully", HttpStatus.OK);
		}else if(teknisiPhotoService.TeknisiPhotoIdExists(id) != true) {
			return new ResponseEntity<>("TeknisiPhoto ID is not exist", HttpStatus.BAD_REQUEST);
		}else if(teknisiService.TeknisiIdExists(teknisi_id) != true){
			return new ResponseEntity<>("Teknisi ID is not exist", HttpStatus.BAD_REQUEST);
		}else if (teknisiPhoto.getId() == null || teknisiPhoto.getTeknisi_id() == 0) {
			return new ResponseEntity<>("Request ID cannot be empty or Teknisi ID cannot be zero", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Delete TeknisiPhoto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been deleted"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/delete/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> deleteTeknisiPhoto(@Valid @PathVariable("id") Long id) {
		if(teknisiPhotoService.TeknisiPhotoIdExists(id) == true) {
			teknisiPhotoService.deleteTeknisiPhotoById(id);
			return new ResponseEntity<>("TeknisiPhoto has been deleted", HttpStatus.OK);
		}else if (teknisiPhotoService.getTeknisiPhotoById(id) == null ) {
			return new ResponseEntity<>("TeknisiPhoto ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("TeknisiPhoto ID did not exist", HttpStatus.BAD_REQUEST);
		}
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
