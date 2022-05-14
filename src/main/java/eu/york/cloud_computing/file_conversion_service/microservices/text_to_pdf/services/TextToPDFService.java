package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Responsible for managing the conversion process of text to pdf's
@Service
public class TextToPDFService {
	// Attributes

	// Methods
	public String c2f(String input) {
		double celsius = Double.parseDouble(input);
		double res = celsius*1.8+32;

		return "{\"input\":\"" + input + "\", \"res\": \"" + res + "\"}";
	}

}

