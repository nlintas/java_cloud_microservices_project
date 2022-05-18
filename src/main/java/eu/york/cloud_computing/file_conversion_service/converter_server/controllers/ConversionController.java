package eu.york.cloud_computing.file_conversion_service.converter_server.controllers;

import java.io.InputStream;
import java.util.logging.Logger;

import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFCommunicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// Accesses the WebC2FService to get results which are then sent to the client as json
@Controller
public class ConversionController {
    protected TextToPDFCommunicationService c2fService;

    protected Logger logger = Logger.getLogger(ConversionController.class.getName());

    public ConversionController(TextToPDFCommunicationService c2fService) {
        this.c2fService = c2fService;
    }

    @RequestMapping("/c2f")
    public void doC2F(@RequestParam(defaultValue = "0") String input) {

        c2fService.c2f(input);

//		logger.info("Res: " + res);
//		model.addAttribute("json", res);
    }


}
