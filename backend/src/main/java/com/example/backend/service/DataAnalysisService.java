package com.example.backend.service;

import com.example.backend.communication.DataAnalysisServiceRequest;
import com.example.backend.communication.DataAnalysisServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class DataAnalysisService {
    public DataAnalysisServiceResponse callService(DataAnalysisServiceRequest input) {
        String output = "Internal Service 2 output" + input;
        return new DataAnalysisServiceResponse(output);
    }
}
