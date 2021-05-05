package com.Teknisi.controller;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

import com.Teknisi.exception.DataNotfoundException;
import com.Teknisi.model.Teknisi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TeknisiController {
	private Logger logger = LoggerFactory.getLogger("SpringBootTeknisiApplication");
	
	
	//get
	@RequestMapping(value = "/teknis", method = RequestMethod.GET)
	public ResponseEntity<Object> getTeknisi() {
		Map<Long, Teknisi> teknisiRepo = new HashMap<>();
		
		Date teknisi_1_last_login = new Date();
		Date teknisi_1_created_date = new Date();
		Date teknisi_1_update_date = new Date();

		Teknisi teknisi_1 = new Teknisi();
		teknisi_1.setId(2L);
		teknisi_1.setPhone("081294749377");
		teknisi_1.setName("Tri");
		teknisi_1.setNik("1999071900");
		teknisi_1.setAddress("Jatiasih");
		teknisi_1.setEmail("heliatgw@gmail.com");
		teknisi_1.setCity("Bekasi");
		teknisi_1.setPostal_code("17426");
		teknisi_1.setLast_login(teknisi_1_last_login);
		teknisi_1.setLongitude("150000");
		teknisi_1.setLatitude("12300000");
		teknisi_1.setCreated_date(teknisi_1_created_date);
		teknisi_1.setCreated_by("Daniel");
		teknisi_1.setUpdate_date(teknisi_1_update_date);
		teknisi_1.setUpdate_by("Najoan");
//		teknisiRepo.put(teknisi_1.getId(), teknisi_1);
		teknisiRepo.put(teknisi_1.getId(), teknisi_1);

		return new ResponseEntity<>(teknisiRepo.values(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/teknis/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable("id") String id) {
		logger.debug("Get with id : " + id);
		if(id.equalsIgnoreCase("1")) throw new DataNotfoundException();
		
		Date teknisi_1_last_login = new Date();
		Date teknisi_1_created_date = new Date();
		Date teknisi_1_update_date = new Date();
		
		Teknisi teknisi_1 = new Teknisi();
		teknisi_1.setId(2L);
		teknisi_1.setPhone("081294749377");
		teknisi_1.setName("Tri");
		teknisi_1.setNik("1999071900");
		teknisi_1.setAddress("Jatiasih");
		teknisi_1.setEmail("heliatgw@gmail.com");
		teknisi_1.setCity("Bekasi");
		teknisi_1.setPostal_code("17426");
		teknisi_1.setLast_login(teknisi_1_last_login);
		teknisi_1.setLongitude("150000");
		teknisi_1.setLatitude("12300000");
		teknisi_1.setCreated_date(teknisi_1_created_date);
		teknisi_1.setCreated_by("Daniel");
		teknisi_1.setUpdate_date(teknisi_1_update_date);
		teknisi_1.setUpdate_by("Najoan");
		
		return new ResponseEntity<>(teknisi_1, HttpStatus.OK);
	}
	
	
	//delete
	@RequestMapping(value = "/teknis/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		logger.debug("Delete with id : " + id);
		return new ResponseEntity<>("Teknis is deleted successsfully", HttpStatus.OK);
	}
	
	
	//post
	@RequestMapping(value = "/teknis", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody Teknisi teknisi_1) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(teknisi_1));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Teknisi is created successsfully", HttpStatus.OK);
	}
	
	
//	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
//		File convertFile = new File(file.getOriginalFilename());
//		convertFile.createNewFile();
//		FileOutputStream fout = new FileOutputStream(convertFile);
//		fout.write(file.getBytes());
//		fout.close();
//		return "File is upload successfully";
//	}
	
	
	//put
	@RequestMapping(value = "/teknis", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Teknisi teknisi_1) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(teknisi_1));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Teknisi is updated successsfully", HttpStatus.OK);
	}
	
	
	
}

