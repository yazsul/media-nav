package com.example.backend.rest;

import java.io.IOException;

import com.example.backend.communication.*;
import com.example.backend.domain.Article;
import com.example.backend.service.ArticleScraperService;
import com.example.backend.service.DataAnalysisService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GatewayController {

    private static final String POST_URL_TONALITY = "https://localhost:9090/"; //todo data analysis host
    private static final String POST_URL_LEANING = "https://localhost:9090/"; //todo data analysis host
    private static final String POST_URL_WORDCLOUD = "https://localhost:9090/"; //todo data analysis host

    private final ArticleScraperService articleScraperService;
    private final DataAnalysisService dataAnalysisService;

    public GatewayController(ArticleScraperService articleScraperService, DataAnalysisService dataAnalysisService) {
        this.articleScraperService = articleScraperService;
        this.dataAnalysisService = dataAnalysisService;
    }

    @GetMapping(value = "/call-scraping-service", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapingService(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        // send an url in request to service
        ScraperServiceRequest scraperServiceRequest = new ScraperServiceRequest(gatewayRequest.getRequestForScrappingService());
        // response
        Article responseFromScrapingService = articleScraperService.getArticleFromCustomUrl(scraperServiceRequest.getRequest());
        // normally one response will be returned, but for testing in the prototype:
        GatewayResponse gatewayResponse = new GatewayResponse(responseFromScrapingService.toString(), "");

        return ResponseEntity.ok(gatewayResponse);
    }

    @PostMapping(value = "/predict-tonality")
    public ResponseEntity<DataAnalysisServiceTonalityLeaningResponse> handleTonalityPrediction(@RequestBody GatewayRequest gatewayRequest) throws JSONException, IOException {
        DataAnalysisServiceTonalityLeaningResponse.Probabilities probabilities =
                dataAnalysisService.predictTonalityAndLeaning(POST_URL_TONALITY, gatewayRequest.getRequestForDataAnalysisService());
        DataAnalysisServiceTonalityLeaningResponse dataAnalysisServiceTonalityLeaningResponse = new DataAnalysisServiceTonalityLeaningResponse(probabilities);
        return ResponseEntity.ok(dataAnalysisServiceTonalityLeaningResponse);
    }

    @PostMapping(value = "/predict-leaning")
    public ResponseEntity<DataAnalysisServiceTonalityLeaningResponse> handleLeaningPrediction(@RequestBody GatewayRequest gatewayRequest) throws JSONException, IOException {
        DataAnalysisServiceTonalityLeaningResponse.Probabilities probabilities =
                dataAnalysisService.predictTonalityAndLeaning(POST_URL_LEANING, gatewayRequest.getRequestForDataAnalysisService());
        DataAnalysisServiceTonalityLeaningResponse dataAnalysisServiceTonalityLeaningResponse = new DataAnalysisServiceTonalityLeaningResponse(probabilities);
        return ResponseEntity.ok(dataAnalysisServiceTonalityLeaningResponse);
    }

    @PostMapping(value = "/wordcloud")
    public ResponseEntity<GatewayResponse> handleWordcloud(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        String responseImg = dataAnalysisService.getWordcloud(POST_URL_WORDCLOUD, gatewayRequest.getRequestForDataAnalysisService());
        GatewayResponse gatewayResponse = new GatewayResponse(responseImg, "");
        return ResponseEntity.ok(gatewayResponse);
    }

}
