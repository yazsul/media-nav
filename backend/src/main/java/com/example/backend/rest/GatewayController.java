package com.example.backend.rest;

import java.io.IOException;
import java.util.regex.Pattern;

import com.example.backend.communication.GatewayRequest;
import com.example.backend.communication.GatewayResponse;
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
        Article response = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());

        GatewayResponse gatewayResponse = new GatewayResponse(response.toString(), "");
        return ResponseEntity.ok(gatewayResponse);
    }

    @GetMapping(value = "/predict-tonality", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToTonalityEndpoint(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        String response = dataAnalysisService.predictTonality(gatewayRequest.getRequestForDataAnalysisService());

        GatewayResponse gatewayResponse = new GatewayResponse("", response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @GetMapping(value = "/predict-leaning", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToLeaningEndpoint(@RequestBody GatewayRequest gatewayRequest) throws IOException{
        String response = dataAnalysisService.predictLeaning(gatewayRequest.getRequestForDataAnalysisService());

        GatewayResponse gatewayResponse = new GatewayResponse("", response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @GetMapping(value = "/wordcloud", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToWorldCloudEndpoint(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        String response = dataAnalysisService.getWordCloud(gatewayRequest.getRequestForDataAnalysisService());

        GatewayResponse gatewayResponse = new GatewayResponse("", response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @GetMapping(value = "/scrape-article-and-predict-tonality", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapThenPredictTonality(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        Article scrappedArticle = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());
        String articleContent =  scrappedArticle.getContent().toString().replaceAll("\\p{Cntrl}", "").replaceAll("[^a-zA-Z0-9_ -]", "");

        String response = dataAnalysisService.predictTonality(articleContent);

        GatewayResponse gatewayResponse = new GatewayResponse("", response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @GetMapping(value = "/scrape-article-and-predict-leaning", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapThenPredictLeaning(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        Article scrappedArticle = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());
        String articleContent =  scrappedArticle.getContent().toString().replaceAll("\\p{Cntrl}", "").replaceAll("[^a-zA-Z0-9_ -]", "");

        String response = dataAnalysisService.predictLeaning(articleContent);

        GatewayResponse gatewayResponse = new GatewayResponse("", response);
        return ResponseEntity.ok(gatewayResponse);
    }

    @GetMapping(value = "/scrape-article-and-get-wordcloud", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<GatewayResponse> handleRequestToScrapThenGetWordcloud(@RequestBody GatewayRequest gatewayRequest) throws IOException {
        Article scrappedArticle = articleScraperService.getArticleFromCustomUrl(gatewayRequest.getRequestForScrappingService());
        String articleContent =  scrappedArticle.getContent().toString().replaceAll("\\p{Cntrl}", "").replaceAll("[^a-zA-Z0-9_ -]", "");

        String response = dataAnalysisService.getWordCloud(articleContent);

        GatewayResponse gatewayResponse = new GatewayResponse("", response);
        return ResponseEntity.ok(gatewayResponse);
    }

}
