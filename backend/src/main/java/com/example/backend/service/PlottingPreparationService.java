package com.example.backend.service;

import com.example.backend.communication.PlottingPreparationServiceRequest;
import com.example.backend.communication.PlottingPreparationServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class PlottingPreparationService {
    public PlottingPreparationServiceResponse callService(PlottingPreparationServiceRequest request) {
        String response = "Internal Service 3 response";
        return new PlottingPreparationServiceResponse(response);
    }
}
