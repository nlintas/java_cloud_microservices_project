package eu.york.cloud_computing.file_conversion_service.client.controllers;

import eu.york.cloud_computing.file_conversion_service.client.services.UserResponderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {UserController.class, UserResponderService.class})
class UserControllerTest {
    @MockBean
    UserResponderService userResponderService;
    UserController userController = new UserController(userResponderService);

    // Integration Test
    @Test
    void controllerNotNull() {
        assertNotNull(userController);
    }
}