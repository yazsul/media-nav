package com.example.backend;

import com.example.backend.communication.GatewayRequest;
import com.example.backend.communication.GatewayResponse;
import com.example.backend.domain.Article;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void testCallScrapingService() throws Exception {
        String url = "https://www.nytimes.com/2023/05/07/world/europe/netherlands-ajax-antisemitism.html";

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("requestForScrappingService", url);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/call-scraping-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        GatewayResponse gatewayResponse = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(gatewayResponse.getResponse());
    }

    @Test
    public void testPredictTonality() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("requestForDataAnalysisService", "An example sentence.");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/predict-tonality")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        GatewayResponse gatewayResponse = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(gatewayResponse.getResponse());
    }

    @Test
    public void testPredictLeaning() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("requestForDataAnalysisService", "An example sentence.");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/predict-leaning")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        GatewayResponse gatewayResponse = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(gatewayResponse.getResponse());
    }

    @Test
    public void testWordCloud() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("requestForDataAnalysisService", "It seems like black cherry and full-bodied are the most mentioned " +
                "characteristics, and Cabernet Sauvignon is the most popular of them all. This aligns with the fact that " +
                "Cabernet Sauvignon \\\"is one of the world's most widely recognized red wine grape varieties. " +
                "It is grown in nearly every major wine-producing country among a diverse spectrum of climates " +
                "from Canada's Okanagan Valley to Lebanon's Beqaa Valley");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/wordcloud")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        GatewayResponse gatewayResponse = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(gatewayResponse.getResponse());
    }

    @Test
    public void testScrapeArticleAndPredictTonality() throws Exception {
        String url = "https://www.nytimes.com/2023/05/07/world/europe/netherlands-ajax-antisemitism.html";

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("requestForScrappingService", url);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/scrape-article-and-predict-tonality")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        GatewayResponse gatewayResponse = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(gatewayResponse.getResponse());
    }

    @Test
    public void testScrapeArticleAndPredictLeaning() throws Exception {
        String url = "https://www.nytimes.com/2023/05/07/world/europe/netherlands-ajax-antisemitism.html";

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("requestForScrappingService", url);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/scrape-article-and-predict-leaning")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        GatewayResponse gatewayResponse = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(gatewayResponse.getResponse());
    }

    @Test
    public void testScrapeArticleAndGetWordCloud() throws Exception {
        String url = "https://www.nytimes.com/2023/05/07/world/europe/netherlands-ajax-antisemitism.html";

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("requestForScrappingService", url);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/scrape-article-and-get-wordcloud")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        GatewayResponse gatewayResponse = objectMapper.readValue(response, new TypeReference<>(){});

        assertNotNull(gatewayResponse.getResponse());
    }
}