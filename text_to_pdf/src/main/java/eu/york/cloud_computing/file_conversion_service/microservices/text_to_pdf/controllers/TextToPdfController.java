package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.helpers.ExceptionResponseBuilder;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    // Endpoints
    @RequestMapping(method = RequestMethod.GET, value = "/txt2pdf")
    public ResponseEntity<?> serveTextToPdf(String input) {
        try {
            // Get converted result
            byte[] res;
            res = this.textToPDFService.convertTextToPdf(input);
            // Create pdf name based on the date and time (flexible for frequent users, no duplicate names)
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            // Prepare Headers to open PDF on the client and allow downloading.
            String headerKey = "Content-Disposition";
            String headerValue = "inline; filename=pdf_" + currentDateTime + ".pdf";
            // Send a successful response
            return ResponseEntity.ok()
                    .header(headerKey, headerValue)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(res);
        }
        // Catch exceptions from the text to pdf service and send a context-full response.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), TextToPdfController.class.getSimpleName());
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pdf2image")
    public ResponseEntity<?> servePdfToImage(MultipartFile input) {
        try {
            // Get converted result
            byte[] res;
            res = this.textToPDFService.convertPdftoImage(input.getBytes());
            // Create image name based on the date and time (flexible for frequent users, no duplicate names)
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            // Prepare Headers
            String headerKey = "Content-Disposition";
            String headerValue = "filename=image_" + currentDateTime + ".jpeg";
            // Send a successful response
            return ResponseEntity.ok()
                    .header(headerKey, headerValue)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(res);
        }
        // Catch exceptions from the text to pdf service and send a context-full response.
        catch (Exception exception) {
            Map<String, String> body = ExceptionResponseBuilder.buildExceptionResponse(exception.toString(), TextToPdfController.class.getSimpleName());
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body);
        }
    }
}
