package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import java.util.logging.Logger;

import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFCommunicationService;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Not to be used individually, should be accessed by the web server.
public class TextToPdfController {
	// Attributes
	protected Logger logger = Logger.getLogger(TextToPdfController.class.getName());
	protected TextToPDFService textToPDFService;

	// Constructor
	public TextToPdfController(TextToPDFService textToPDFService) {
		this.textToPDFService = textToPDFService;
	}

	// Routes
	@RequestMapping("/c2f")
	public String doC2F(@RequestParam(defaultValue="0") String input) {
		return textToPDFService.c2f(input);
	}

}
