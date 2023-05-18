package com.example.backend.rest;

import java.io.IOException;

import com.example.backend.communication.GatewayRequest;
import com.example.backend.communication.GatewayResponse;
import com.example.backend.communication.ScraperServiceRequest;
import com.example.backend.domain.Article;
import com.example.backend.service.ArticleScraperService;
import com.example.backend.service.DataAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

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

}
