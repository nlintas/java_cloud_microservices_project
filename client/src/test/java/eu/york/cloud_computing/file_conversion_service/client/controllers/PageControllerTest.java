package eu.york.cloud_computing.file_conversion_service.client.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PageController.class)
@AutoConfigureMockMvc
class PageControllerTest {
    @Autowired
    MockMvc mockMvc;

    PageController pageController = new PageController();

    // Unit Test
    @Test
    void controllerNotNull() {
        assertNotNull(pageController);
    }

    // Integration Test
    @Test
    void controllerRedirectSuccess() throws Exception {
        mockMvc.perform(get("/").content("index")).andExpect(status().isOk()).andExpect(forwardedUrl("index"));
    }
}