package eu.york.cloud_computing.file_conversion_service.converter_server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Provides access to the home page for the server
@Controller
public class HomeController {
	@RequestMapping("/")
	public String home() {
		return "index";
	}

}
