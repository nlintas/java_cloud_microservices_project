package eu.york.cloud_computing.file_conversion_service.converter_server.controllers;

import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFPortal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    // Attributes
    protected TextToPDFPortal textToPDFPortal;

    // Constructor
    public UserController(TextToPDFPortal textToPDFPortal) {
        this.textToPDFPortal = textToPDFPortal;
    }

    // Endpoints
    @RequestMapping(method = RequestMethod.POST, value = "/txt2pdf")
    public ResponseEntity<byte[]> requestTextToPdf(String input) {
        ResponseEntity<byte[]> res = textToPDFPortal.sendTextToPDFRequest(input);
        return res;
    }
}
