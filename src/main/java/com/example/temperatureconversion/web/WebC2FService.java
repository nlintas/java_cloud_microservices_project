package com.example.temperatureconversion.web;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Responsible for communicating with the c2f controller
@Service
public class WebC2FService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	protected Logger logger = Logger.getLogger(WebC2FService.class.getName());

	/**
	 * Constructor - initialises URI that will be used in the request to the server-side microservice
	 * Note that the uri uses the remote service's 'logical' name (c2f-service).
	 * This is possible due to the @LoadBalanced annotation that autowires a Ribbon client-side load balancer.
	 */
	public WebC2FService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	//Append URI with appropriate target path and parameter (the latter is obtained (as a string) from the 'input' textbox of the index.html UI)
	public String c2f(String input) {
		return restTemplate.getForObject(serviceUrl + "/c2f?input={input}", String.class, input);
	}

}

