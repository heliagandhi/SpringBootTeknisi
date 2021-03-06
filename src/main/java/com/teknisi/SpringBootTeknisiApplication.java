package com.teknisi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.teknisi.config.DisableWarnings;
import com.teknisi.services.TeknisiService;

@SpringBootApplication
@EnableScheduling
public class SpringBootTeknisiApplication implements CommandLineRunner {
	
	@Autowired
	TeknisiService teknisiService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTeknisiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		DisableWarnings.disableAccessWarnings();
	}

}

//http://localhost:8181/swagger-ui.html#/
//http://localhost:8181/register
