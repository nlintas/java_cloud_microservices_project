package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Not to be used individually, should be accessed by the web server.
public class TextToPdfController {
	protected Logger logger = Logger.getLogger(TextToPdfController.class.getName());

	@RequestMapping("/c2f")
	public String doC2F(@RequestParam(defaultValue="0") String input) {

		double celsius = Double.valueOf(input);
		double res = celsius*1.8+32;

		return "{\"input\":\"" + input + "\", \"res\": \"" + res + "\"}";
	}

}
