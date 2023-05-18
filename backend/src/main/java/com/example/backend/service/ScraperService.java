package com.example.backend.service;

import com.example.backend.communication.ScraperServiceRequest;
import com.example.backend.communication.ScraperServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {

    private final ArticleScraperService articleScraperService;

    public ScraperService(ArticleScraperService articleScraperService) {
        this.articleScraperService = articleScraperService;
    }

    public ScraperServiceResponse callService(ScraperServiceRequest input) {
        String output = "Internal Service 1 output" + input + " yes yes yes that was it";
        return new ScraperServiceResponse(output);
    }
}
