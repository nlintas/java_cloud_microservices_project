package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	// Provide access to the index page.
	@RequestMapping("/")
	public String home() {
		return "index";
	}
}