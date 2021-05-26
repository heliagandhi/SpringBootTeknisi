package com.Teknisi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "/hello", tags = "App User Profile Controller")
@RestController
public class HelloWorldController {
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello Helia";
	}
}
