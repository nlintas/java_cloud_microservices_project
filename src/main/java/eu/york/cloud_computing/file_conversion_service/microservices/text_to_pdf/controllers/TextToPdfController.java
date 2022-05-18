package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import com.lowagie.text.Document;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
// Not to be used individually, should be accessed by the web server.
public class TextToPdfController {
    // Attributes
    protected Logger logger = Logger.getLogger(TextToPdfController.class.getName());
    protected TextToPDFService textToPDFService;

    // Constructor
    public TextToPdfController(TextToPDFService textToPDFService) {
        this.textToPDFService = textToPDFService;
    }

    // Routes
    @RequestMapping("/c2f")
    public void doC2F(HttpServletResponse response) throws IOException {
        // Create pdf name
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        // Set Headers
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        // Return
        this.textToPDFService.c2f(response);
    }

}
