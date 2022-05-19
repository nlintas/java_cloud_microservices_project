package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf;

import org.junit.jupiter.api.Test;

class TextToPdfServerTest {

    // Integration Tests
    @Test
    void textToPdfServerLoads() {
        TextToPdfServer.main(new String[] {});
    }
}