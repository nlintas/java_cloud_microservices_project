package eu.york.cloud_computing.file_conversion_service.client.controllers;

import eu.york.cloud_computing.file_conversion_service.client.services.TextToPDFPortal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {UserController.class, TextToPDFPortal.class})
class UserControllerTest {
    @MockBean
    TextToPDFPortal textToPDFPortal;
    UserController userController = new UserController(textToPDFPortal);

    @Test
    void controllerNotNull() {
        assertNotNull(userController);
    }
}