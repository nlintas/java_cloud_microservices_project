package com.example.temperatureconversion.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Provides access to the home page for the server
@Controller
public class HomeController {

	/**
	 * Thymeleaf is used for creating the Web app UI.
	 * Thymeleaf is a Java template engine for processing and creating HTML, XML, JavaScript, CSS, and text.
	 * The files to be processed must be included in the "templates" folder under src/main/resources.
	 * More information on how to paramterise Thymeleaf can be found at https://www.baeldung.com/thymeleaf-in-spring-mvc
	 */
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}

}
