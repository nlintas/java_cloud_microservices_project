package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import eu.york.cloud_computing.file_conversion_service.client.helpers.ExceptionResponseBuilder;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class TextToPdfController {
    // Attributes
    protected TextToPDFService textToPDFService;

    // Constructor
    public TextToPdfController(TextToPDFService textToPDFService) {
        this.textToPDFService = textToPDFService;
    }

    // Routes
    @RequestMapping(method = RequestMethod.GET, value = "/txt2pdf")
    public ResponseEntity<?> serveTextToPdf(String input) {
        try {
            // Create pdf name
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            // Prepare Headers
            String headerKey = "Content-Disposition";
            String headerValue = "inline; filename=pdf_" + currentDateTime + ".pdf";
            // Get converted result
            byte[] res;
            res = this.textToPDFService.convertTextToPdf(input);
            // Send a response
            return ResponseEntity.ok()
                    .header(headerKey, headerValue)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(res);
        }
        // Catch any thrown exceptions and send a relevant response with a context.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), TextToPdfController.class.getSimpleName());
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(body);
        }
    }
}
