package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.helpers;

import org.junit.jupiter.api.Test;
import eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.helpers.ExceptionResponseBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionResponseBuilderTest {

    @Test
    void buildExceptionResponse() {
        Map<String, String> res = ExceptionResponseBuilder.buildExceptionResponse("test", "someone");
        String expectedInString = "someone";
        String expectedInString2 = "test";
        // Make sure its not null
        assertNotNull(res);
        // Check if result contains the given parameters
        assertTrue(res.toString().contains(expectedInString) && res.toString().contains(expectedInString2));
    }
}