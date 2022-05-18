package eu.york.cloud_computing.file_conversion_service.converter_server.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFTerminalService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Accesses the WebC2FService to get results which are then sent to the client as json
@Controller
public class ConversionController {
    // Attributes
    protected TextToPDFTerminalService c2fService;

    // Constructor
    public ConversionController(TextToPDFTerminalService c2fService) {
        this.c2fService = c2fService;
    }

    // Endpoints
    @RequestMapping("/c2f")
    public ResponseEntity<byte[]> doC2F(@RequestParam(defaultValue = "0") String input) {
            return c2fService.c2f(input);
    }
}
