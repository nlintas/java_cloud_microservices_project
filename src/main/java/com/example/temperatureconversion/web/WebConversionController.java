package com.example.temperatureconversion.web;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Accesses the WebC2FService to get results which are then sent to the client as json
@Controller
public class WebConversionController {
	
	//@Autowired //autowires c2fservice bean for usage in this bean
	protected WebC2FService c2fService;
	
	protected Logger logger = Logger.getLogger(WebConversionController.class.getName());
	
	/**
	 * Constructor - sets up c2fService object for creating the request URI
	 */
	public WebConversionController(WebC2FService c2fService) {
		this.c2fService = c2fService;
	}
	
	/**
	 * Uses the c2FService object to send request to the server-side microservice.
	 * It then uses the string returned as response to update the 'model' (i.e. the data representation).
	 * The 'model' is an object that contains a representation of the application's data (data can be in any form - string, database information, objects, etc.)
	 * Here the controller adds an attribute named "json" to the representation whose value is equal to the string that corresponds to the temperature conversion.
	 * This is then automatically fed to res.html for display.
	 */
	@RequestMapping("/c2f")
	public String doC2F(@RequestParam(defaultValue="0") String input, Model model) {

		String res = c2fService.c2f(input);

		logger.info("Res: " + res);
		model.addAttribute("json", res);

		return "res";
	}



}
