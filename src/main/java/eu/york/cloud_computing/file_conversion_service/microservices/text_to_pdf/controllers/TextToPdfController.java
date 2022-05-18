package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@RestController
// Not to be used individually, should be accessed by the web server.
public class TextToPdfController {
    // Attributes
    protected TextToPDFService textToPDFService;

    // Constructor
    public TextToPdfController(TextToPDFService textToPDFService) {
        this.textToPDFService = textToPDFService;
    }

    // Routes
    @RequestMapping("/c2f")
    public ResponseEntity<byte[]> doC2F() {
        // Create pdf name
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        // Prepare Headers
        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=pdf_" + currentDateTime + ".pdf";
        // Get converted result
        byte[] res = this.textToPDFService.c2f();
        // Send a response
        return ResponseEntity.ok()
                .header(headerKey, headerValue)
                .contentType(MediaType.APPLICATION_PDF)
                .body(res);
    }

}
