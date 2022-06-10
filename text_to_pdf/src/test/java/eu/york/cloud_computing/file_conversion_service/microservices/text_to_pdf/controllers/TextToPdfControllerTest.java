package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {TextToPdfController.class, TextToPDFService.class})
class TextToPdfControllerTest {
    TextToPDFService textToPDFService = new TextToPDFService();
    TextToPdfController textToPdfController = new TextToPdfController(textToPDFService);

    @Test
    void serveTextToPdfGivenNullReturnsNonNullStatusSuccess() throws Exception {
        ResponseEntity<?> res = textToPdfController.serveTextToPdf(null);
        // Not Null
        assertNotNull(res);
        // Appropriate status code received
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}