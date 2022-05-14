package com.example.temperatureconversion.c2f;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Provides access to the home page of the server.
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}

}