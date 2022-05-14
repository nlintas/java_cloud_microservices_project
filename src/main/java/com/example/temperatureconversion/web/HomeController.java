package com.example.temperatureconversion.web;

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
