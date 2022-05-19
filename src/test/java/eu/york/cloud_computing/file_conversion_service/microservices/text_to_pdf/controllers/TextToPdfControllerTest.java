package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.controllers;

import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services.TextToPDFService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class TextToPdfControllerTest {
    TextToPDFService textToPDFService = new TextToPDFService();
    TextToPdfController textToPdfController = new TextToPdfController(textToPDFService);

    @Test
    void serveTextToPdfReturnsPDFStatusSuccess() {
        ResponseEntity<byte[]> res = textToPdfController.serveTextToPdf("hello");
        // Appropriate status code received
        assertEquals(res.getStatusCode(), HttpStatus.OK);
        // Appropriate content disposition received
        String expectedContentDis = ContentDisposition.builder("inline").build().toString();
        String resContentDis = res.getHeaders().getContentDisposition().toString();
        assertTrue(resContentDis.contains(expectedContentDis));
        // Appropriate media type
        String expectedMediaType = MediaType.APPLICATION_PDF.toString();
        String resMediaType = res.getHeaders().getContentType().toString();
        assertEquals(resMediaType, expectedMediaType);
    }

    @Test
    void serveTextToPdfGivenNullReturnsNonNullStatusSuccess() {
        ResponseEntity<byte[]> res = textToPdfController.serveTextToPdf(null);
        // Not Null
        assertNotNull(res);
        // Appropriate status code received
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}