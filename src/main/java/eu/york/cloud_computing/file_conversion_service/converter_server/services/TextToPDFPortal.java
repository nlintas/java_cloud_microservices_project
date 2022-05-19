package eu.york.cloud_computing.file_conversion_service.converter_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

// This class is untestable as of now (all logic depends on a response from another service).
@Service
public class TextToPDFPortal {
	// Attributes
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	// Constructor
	public TextToPDFPortal(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	// Endpoints
	// Sends a request for a txt2pdf conversion to an external microservice.
	public ResponseEntity<byte[]> sendTextToPDFRequest(String input) {
		// Setup Headers & URL
		String url = serviceUrl + "/txt2pdf?input={input}";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		// Get Result from Microservice
		return restTemplate.postForEntity(url, entity, byte[].class, input);
	}

}

