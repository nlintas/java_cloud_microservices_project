package eu.york.cloud_computing.file_conversion_service.client.controllers;

import eu.york.cloud_computing.file_conversion_service.client.helpers.ExceptionResponseBuilder;
import eu.york.cloud_computing.file_conversion_service.client.services.UserResponderService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
    //
    @RequestMapping(method = RequestMethod.GET, value = "/txt2pdf")
    public ResponseEntity<?> requestTextToPdf(String input) {
        // Send request through the service to the text to pdf microservice.
        try {
            byte[] res;
            res = userResponderService.sendTextToPDFRequest(input);
            // Create pdf name based on the date and time
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            // Prepare Headers signify its caring a pdf.
            HttpHeaders headers = new HttpHeaders();
            ContentDisposition contentDisposition = ContentDisposition.builder("inline").filename(currentDateTime + ".pdf").build();
            headers.setContentDisposition(contentDisposition);
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Send a successful response
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(res);
        }
        // Catch exceptions from the user responder service and send a context-full response.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), UserController.class.getSimpleName());
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(body);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pdf2image")
    public ResponseEntity<?> requestPdfToImage(MultipartFile input) {
        // Send request through the service to the pdf to image microservice.
        try {
            byte[] res;
            res = userResponderService.sendPdfToImageRequest(input.getBytes());
            // Create image name based on the date and time (flexible for frequent users, no duplicate names)
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            // Set request headers
            HttpHeaders headers = new HttpHeaders();
            // Set name of zip file
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(currentDateTime + " images.zip").build();
            headers.setContentDisposition(contentDisposition);
            // Set response type
            headers.setContentType(MediaType.valueOf("application/zip"));
            // Send a successful response
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(res);
        }
        // Catch exceptions from the user responder service and send a context-full response.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), UserController.class.getSimpleName());
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(body);
        }
    }
}
