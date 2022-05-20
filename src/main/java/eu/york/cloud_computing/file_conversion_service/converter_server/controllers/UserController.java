package eu.york.cloud_computing.file_conversion_service.converter_server.controllers;

import eu.york.cloud_computing.file_conversion_service.converter_server.helpers.ExceptionResponseBuilder;
import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFPortal;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> requestTextToPdf(String input) {
        try {
            return textToPDFPortal.sendTextToPDFRequest(input);
        }
        // Catch any thrown exceptions and send a relevant response with a context.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), UserController.class.getSimpleName());
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(body);
        }
    }
}
