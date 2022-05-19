package eu.york.cloud_computing.file_conversion_service.eureka_registrator;

import org.junit.jupiter.api.Test;

class EurekaServerTest {

    // Integration Tests
    @Test
    void eurekaServerLoads() {
        EurekaServer.main(new String[] {});
    }
}