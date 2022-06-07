package eu.york.cloud_computing.file_conversion_service.client.controllers;

import eu.york.cloud_computing.file_conversion_service.client.helpers.ExceptionResponseBuilder;
import eu.york.cloud_computing.file_conversion_service.client.services.UserResponderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class UserController {
    // Attributes
    protected UserResponderService userResponderService;

    // Constructor
    public UserController(UserResponderService userResponderService) {
        // Add the service to the controller during creation
        this.userResponderService = userResponderService;
    }

    // Endpoints
    // Expect a GET request.
    @RequestMapping(method = RequestMethod.GET, value = "/txt2pdf")
    public ResponseEntity<?> requestTextToPdf(String input) {
        // Send request through the service to the text to pdf microservice.
        try {
            return userResponderService.sendTextToPDFRequest(input);
        }
        // Catch exceptions from the user responder service and send a context-full response.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), UserController.class.getSimpleName());
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(body);
        }
    }

    // Provide access to the pdf to image conversion page.
    @RequestMapping("/pdf_image")
    public String pdfToImagePageHandler() {
        return "pdf2image";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pdf2image")
    public ResponseEntity<?> pdfToImageRequestHandler(byte[] input) {
        // Send request through the service to the pdf to image microservice.
        try {
            return userResponderService.sendPdfToImageRequest(input);
        }
        // Catch exceptions from the user responder service and send a context-full response.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), UserController.class.getSimpleName());
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(body);
        }
    }
}
