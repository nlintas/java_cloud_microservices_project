package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.responses.ExceptionRes;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.responses.PdfToImageRes;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.responses.TextToPdfFRes;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
            // Create pdf name based on the date and time
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            // Prepare Headers signify its caring a pdf.
            HttpHeaders headers = new HttpHeaders();
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(currentDateTime + " .pdf").build();
            headers.setContentDisposition(contentDisposition);
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Assemble HATEOAS response (Converts to Base64 String automatically)
            TextToPdfFRes response = new TextToPdfFRes(res);
            // Link to myself and the other endpoints
            response.add(linkTo(methodOn(TextToPdfController.class).serveTextToPdf(input)).withSelfRel());
            response.add(linkTo(methodOn(TextToPdfController.class).servePdfToImage(null)).withRel("pdf2image"));
            // Send response
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(response);
        }
        // Catch exceptions from the text to pdf service and send a context-full response.
        catch (Exception exception) {
            // Create Exception Response
            ExceptionRes response = new ExceptionRes(exception.toString(), TextToPdfController.class.getSimpleName());
            // Link to myself and the other endpoints
            response.add(linkTo(methodOn(TextToPdfController.class).serveTextToPdf(input)).withSelfRel());
            response.add(linkTo(methodOn(TextToPdfController.class).servePdfToImage(null)).withRel("pdf2image"));
            // Send response
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
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
            // Set request headers
            HttpHeaders headers = new HttpHeaders();
            // Set name of zip file
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(currentDateTime + " images.zip").build();
            headers.setContentDisposition(contentDisposition);
            // Set response type
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Assemble HATEOAS response (Converts to Base64 String automatically)
            PdfToImageRes response = new PdfToImageRes(res);
            // Link to myself and the other endpoints
            response.add(linkTo(methodOn(TextToPdfController.class).servePdfToImage(input)).withSelfRel());
            response.add(linkTo(methodOn(TextToPdfController.class).serveTextToPdf(null)).withRel("txt2pdf"));
            // Send a successful response
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(response);
        }
        // Catch exceptions from the text to pdf service and send a context-full response.
        catch (Exception exception) {
            // Create Exception Response
            ExceptionRes response = new ExceptionRes(exception.toString(), TextToPdfController.class.getSimpleName());
            // Link to myself and the other endpoints
            response.add(linkTo(methodOn(TextToPdfController.class).servePdfToImage(input)).withSelfRel());
            response.add(linkTo(methodOn(TextToPdfController.class).serveTextToPdf(null)).withRel("txt2pdf"));
            // Send response
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}
