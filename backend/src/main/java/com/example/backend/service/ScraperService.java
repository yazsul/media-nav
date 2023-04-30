package com.example.backend.service;

import com.example.backend.communication.ScraperServiceRequest;
import com.example.backend.communication.ScraperServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {
    public ScraperServiceResponse callService(ScraperServiceRequest request) {
        String response = "Internal Service 1 response";
        return new ScraperServiceResponse(response);
    }
}
