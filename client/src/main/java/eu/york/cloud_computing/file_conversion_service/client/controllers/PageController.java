package eu.york.cloud_computing.file_conversion_service.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	// Provide access to the index page.
	@RequestMapping("/")
	public String home() {
		return "index";
	}

	// Provide access to the pdf to image conversion page.
	@RequestMapping("/pdf_image")
	public String pdfToImagePageHandler() {
		return "pdf2image";
	}
}
