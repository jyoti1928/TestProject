package com.example.demo.controller;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ConnectedCityService;

@RestController
public class ConnectedCityController {
	
	@Autowired
	private ConnectedCityService connCityService;
	
	@RequestMapping(value = "/connected")
	public String checkIfCitiesAreConnected(@RequestParam("origin") String origin,
			@RequestParam("destination") String destination) throws IOException{
		
		Objects.requireNonNull(origin);
		Objects.requireNonNull(destination);
		return connCityService.checkIfCitiesAreConnected(origin, destination);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String invalidCity(){
		return "no";
	}
}
