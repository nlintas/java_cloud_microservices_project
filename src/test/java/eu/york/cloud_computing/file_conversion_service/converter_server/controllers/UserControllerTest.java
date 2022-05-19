package eu.york.cloud_computing.file_conversion_service.converter_server.controllers;

import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFPortal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerTest {
    TextToPDFPortal textToPDFPortal = new TextToPDFPortal("http://text2pdf-microservice");
    UserController userController = new UserController(textToPDFPortal);

    @Test
    void controllerNotNull(){
        assertNotNull(userController);
    }
}