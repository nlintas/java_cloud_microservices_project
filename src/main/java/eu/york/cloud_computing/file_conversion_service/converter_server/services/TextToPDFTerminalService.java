package eu.york.cloud_computing.file_conversion_service.converter_server.services;

import java.util.Collections;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Responsible for communicating with the c2f controller
@Service
public class TextToPDFTerminalService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	protected Logger logger = Logger.getLogger(TextToPDFTerminalService.class.getName());

	public TextToPDFTerminalService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	public ResponseEntity<byte[]> c2f(String input) {
		String url = serviceUrl + "/c2f?input={input}";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, input);
	}

}

