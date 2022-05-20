package eu.york.cloud_computing.file_conversion_service.converter_server.helpers;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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