package com.example.backend.rest;

import java.io.IOException;
import java.util.regex.Pattern;

import com.example.backend.communication.GatewayRequest;
import com.example.backend.communication.GatewayResponse;
import com.example.backend.domain.Article;
import com.example.backend.service.ArticleScraperService;
import com.example.backend.service.DataAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);
    private final ArticleScraperService articleScraperService;
    private final DataAnalysisService dataAnalysisService;

    public GatewayController(ArticleScraperService articleScraperService, DataAnalysisService dataAnalysisService) {
        this.articleScraperService = articleScraperService;
        this.dataAnalysisService = dataAnalysisService;
    }

    @PostMapping(value = "/call-scraping-service", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapingService(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        logger.info("handleRequestToScrapingService is called and the following parameter is passed: " + gatewayRequest.getRequestForScrappingService());

        Article response = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());

        GatewayResponse gatewayResponse = new GatewayResponse(response.toString());
        return ResponseEntity.ok(gatewayResponse);
    }

    @PostMapping(value = "/predict-tonality", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToTonalityEndpoint(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        logger.info("handleRequestToTonalityEndpoint is called and the following parameter is passed: " + gatewayRequest.getRequestForDataAnalysisService());

        String response = dataAnalysisService.predictTonality(gatewayRequest.getRequestForDataAnalysisService());

        GatewayResponse gatewayResponse = new GatewayResponse(response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @PostMapping(value = "/predict-leaning", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToLeaningEndpoint(@RequestBody GatewayRequest gatewayRequest) throws IOException{
        logger.info("handleRequestToLeaningEndpoint is called and the following parameter is passed: " + gatewayRequest.getRequestForDataAnalysisService());

        String response = dataAnalysisService.predictLeaning(gatewayRequest.getRequestForDataAnalysisService());

        GatewayResponse gatewayResponse = new GatewayResponse(response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @PostMapping(value = "/wordcloud", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToWorldCloudEndpoint(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        logger.info("handleRequestToWorldCloudEndpoint is called and the following parameter is passed: " + gatewayRequest.getRequestForDataAnalysisService());

        String response = dataAnalysisService.getWordCloud(gatewayRequest.getRequestForDataAnalysisService());

        GatewayResponse gatewayResponse = new GatewayResponse(response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @PostMapping(value = "/scrape-article-and-predict-tonality", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapThenPredictTonality(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        logger.info("handleRequestToScrapThenPredictTonality is called and the following parameter is passed: " + gatewayRequest.getRequestForScrappingService());

        Article scrappedArticle = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());
        String articleContent =  scrappedArticle.getContent().toString().replaceAll("\\p{Cntrl}", "").replaceAll("[^a-zA-Z0-9_ -]", "");

        String response = dataAnalysisService.predictTonality(articleContent);

        GatewayResponse gatewayResponse = new GatewayResponse(response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @PostMapping(value = "/scrape-article-and-predict-leaning", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapThenPredictLeaning(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        logger.info("handleRequestToScrapThenPredictLeaning is called and the following parameter is passed: " + gatewayRequest.getRequestForScrappingService());

        Article scrappedArticle = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());
        String articleContent =  scrappedArticle.getContent().toString().replaceAll("\\p{Cntrl}", "").replaceAll("[^a-zA-Z0-9_ -]", "");

        String response = dataAnalysisService.predictLeaning(articleContent);

        GatewayResponse gatewayResponse = new GatewayResponse(response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @PostMapping(value = "/scrape-article-and-get-wordcloud", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapThenGetWordcloud(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        logger.info("handleRequestToScrapThenGetWordcloud is called and the following parameter is passed: " + gatewayRequest.getRequestForScrappingService());

        Article scrappedArticle = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());
        String articleContent =  scrappedArticle.getContent().toString().replaceAll("\\p{Cntrl}", "").replaceAll("[^a-zA-Z0-9_ -]", "");

        String response = dataAnalysisService.getWordCloud(articleContent);

        GatewayResponse gatewayResponse = new GatewayResponse(response);
        return ResponseEntity.ok(gatewayResponse);
    }

}
