package com.example.backend.rest;

import com.example.backend.communication.*;
import com.example.backend.service.ScraperService;
import com.example.backend.service.DataAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {
    private final ScraperService scraperService;
    private final DataAnalysisService dataAnalysisService;

    public GatewayController(ScraperService scraperService, DataAnalysisService dataAnalysisService) {
        this.scraperService = scraperService;
        this.dataAnalysisService = dataAnalysisService;
    }

    @GetMapping(value = "/someEndpoint", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequest(@RequestBody GatewayRequest gatewayRequest) {

        ScraperServiceRequest scraperServiceRequest = new ScraperServiceRequest(gatewayRequest.getRequestForScrappingService());
        DataAnalysisServiceRequest dataAnalysisServiceRequest = new DataAnalysisServiceRequest(gatewayRequest.getRequestForDataAnalysisService());

        ScraperServiceResponse scraperServiceResponse = scraperService.callService(scraperServiceRequest);
        DataAnalysisServiceResponse dataAnalysisServiceResponse = dataAnalysisService.callService(dataAnalysisServiceRequest);

        // normally one response will be returned, but for testing in the prototype:
        GatewayResponse gatewayResponse = new GatewayResponse(scraperServiceResponse.getResponse(),
                dataAnalysisServiceResponse.getResponse());

        return ResponseEntity.ok(gatewayResponse);
    }
}
