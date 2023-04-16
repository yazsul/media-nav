package com.example.backend.rest;

import com.example.backend.communication.*;
import com.example.backend.service.PlottingPreparationService;
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
    private final PlottingPreparationService plottingPreparationService;

    public GatewayController(ScraperService scraperService, DataAnalysisService dataAnalysisService, PlottingPreparationService plottingPreparationService) {
        this.scraperService = scraperService;
        this.dataAnalysisService = dataAnalysisService;
        this.plottingPreparationService = plottingPreparationService;
    }

    @GetMapping(value = "/someEndpoint", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequest(@RequestBody GatewayRequest gatewayRequest) {

        ScraperServiceRequest scraperServiceRequest = new ScraperServiceRequest(gatewayRequest.getRequest1());
        DataAnalysisServiceRequest dataAnalysisServiceRequest = new DataAnalysisServiceRequest(gatewayRequest.getRequest2());
        PlottingPreparationServiceRequest plottingPreparationServiceRequest = new PlottingPreparationServiceRequest(gatewayRequest.getRequest3());

        ScraperServiceResponse response1 = scraperService.callService(scraperServiceRequest);
        DataAnalysisServiceResponse response2 = dataAnalysisService.callService(dataAnalysisServiceRequest);
        PlottingPreparationServiceResponse response3 = plottingPreparationService.callService(plottingPreparationServiceRequest);

        // normally one response will be returned, but for testing in the prototype:
        GatewayResponse gatewayResponse = new GatewayResponse(response1.getResponse(), response2.getResponse(), response3.getResponse());

        return ResponseEntity.ok(gatewayResponse);
    }
}
