package eu.york.cloud_computing.file_conversion_service.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebServerTest {

    // Integration Tests
    @Test
    void webServerLoadsContextNotNull() {
        WebServer.main(new String[] {});
        // Context not null
        assertNotNull(WebServer.applicationContext);
    }
}