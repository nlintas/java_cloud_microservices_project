package eu.york.cloud_computing.file_conversion_service.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;

// This class is untestable as of now (all logic depends on a response from another service).
@Service
public class UserResponderService {
    // Attributes
    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    // Constructor
    public UserResponderService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
    }

    // Endpoints
    // Sends a request for a txt2pdf conversion to an external microservice with the user input.
    public ResponseEntity<?> sendTextToPDFRequest(String input) {
        try {
            // Setup Headers & URL
            String url = serviceUrl + "/txt2pdf?input={input}";
            HttpHeaders headers = new HttpHeaders();
            // Accept PDF media types responses
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            // Send a GET request to the text to pdf microservice's controller with the user input.
            // Expect a byte[] format response.
            return restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send a text to pdf  request to microservice, cause: " + e);
        }
    }

    // Sends a request for a pdf2image conversion to an external microservice with the user input.
    public byte[] sendPdfToImageRequest(byte[] input) {
        try {
            // Setup Headers & URL
            String url = serviceUrl + "/pdf2image?input={input}";
            HttpHeaders headers = new HttpHeaders();
            // Accept PDF media types responses
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            // Send a GET request to the text to pdf microservice's controller with the user input.
            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity, LinkedHashMap.class, input);
            LinkedHashMap<String, String> endpointReturn = response.getBody();
            String pdf;
            if (endpointReturn != null) {
                pdf = endpointReturn.get("pdf");
            } else {
                throw new RuntimeException("Received null pdf response.");
            }
            byte[] decoded = Base64.getDecoder().decode(pdf);
            return decoded;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send a pdf to image request to microservice, cause: " + e);
        }
    }
}