package com.example.backend;

import java.lang.reflect.Type;
import java.util.List;

import com.example.backend.domain.Article;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScraperControllerIT {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // idea bug
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAtriclesInvalidUrl() throws Exception {
        String url = "https://example.com"; // URL to be passed as a path variable
        // Perform a GET request to the endpoint with the URL as a path variable
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/from-url?url="+url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Add more assertions for the response as needed
        String response = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Article article = objectMapper.readValue(response, new TypeReference<>(){});

        assertEquals("", article.getContent());
    }

    @Test
    public void testGetRealArticle() throws Exception {
        String url = "https://www.nytimes.com/2023/05/07/world/europe/netherlands-ajax-antisemitism.html";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/from-url?url="+url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Article article = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(article);
    }
}