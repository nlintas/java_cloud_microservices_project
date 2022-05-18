package eu.york.cloud_computing.file_conversion_service.converter_server.controllers;

import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFPortal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    // Attributes
    protected TextToPDFPortal c2fService;

    // Constructor
    public UserController(TextToPDFPortal c2fService) {
        this.c2fService = c2fService;
    }

    // Endpoints
    @RequestMapping("/txt2pdf")
    public ResponseEntity<byte[]> requestTextToPdf(@RequestParam(defaultValue = "Test Text!") String input) {
            return c2fService.sendTextToPDFRequest(input);
    }
}
