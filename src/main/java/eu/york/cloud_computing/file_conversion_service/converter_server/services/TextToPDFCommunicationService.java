package eu.york.cloud_computing.file_conversion_service.converter_server.services;

import java.awt.*;
import java.util.Collections;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Responsible for communicating with the c2f controller
@Service
public class TextToPDFCommunicationService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	protected Logger logger = Logger.getLogger(TextToPDFCommunicationService.class.getName());

	public TextToPDFCommunicationService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	public void c2f(String input) {
		String url = serviceUrl + "/c2f?input={input}";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));

		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		restTemplate.exchange(url, HttpMethod.GET, entity, MediaType.APPLICATION_PDF.getClass(), input);
	}

}

