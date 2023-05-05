package com.example.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScraperControllerIT {

    // todo fix tests (could not autowire)

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // idea bug
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAtriclesInvalidUrl() throws Exception {
        String url = "https://example.com"; // URL to be passed as a path variable
        // Perform a GET request to the endpoint with the URL as a path variable
        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/{url}", url))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        // Add more assertions for the response as needed
    }
}