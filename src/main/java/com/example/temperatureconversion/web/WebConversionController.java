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
	protected WebC2FService c2fService;
	
	protected Logger logger = Logger.getLogger(WebConversionController.class.getName());
	public WebConversionController(WebC2FService c2fService) {
		this.c2fService = c2fService;
	}

	@RequestMapping("/c2f")
	public String doC2F(@RequestParam(defaultValue="0") String input, Model model) {

		String res = c2fService.c2f(input);

		logger.info("Res: " + res);
		model.addAttribute("json", res);

		return "res";
	}



}
