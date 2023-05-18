package com.example.backend.rest;

import java.io.IOException;

import com.example.backend.communication.*;
import com.example.backend.domain.Article;
import com.example.backend.service.ArticleScraperService;
import com.example.backend.service.PlottingPreparationService;
import com.example.backend.service.ScraperService;
import com.example.backend.service.DataAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {
    private final ArticleScraperService articleScraperService;
    private final DataAnalysisService dataAnalysisService;
    private final PlottingPreparationService plottingPreparationService;

    public GatewayController(ArticleScraperService articleScraperService, DataAnalysisService dataAnalysisService, PlottingPreparationService plottingPreparationService) {
        this.articleScraperService = articleScraperService;
        this.dataAnalysisService = dataAnalysisService;
        this.plottingPreparationService = plottingPreparationService;

    }

    @GetMapping(value = "/call-scraping-service", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapingService(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        // send an url in request to service
        ScraperServiceRequest scraperServiceRequest = new ScraperServiceRequest(gatewayRequest.getRequest1());
        // response
        Article response = articleScraperService.getArticleFromCustomUrl(scraperServiceRequest.getRequest());
        // normally one response will be returned, but for testing in the prototype:
        GatewayResponse gatewayResponse = new GatewayResponse(response.toString(), "", "");

        return ResponseEntity.ok(gatewayResponse);
    }
}
