package com.example.backend.service;

import com.example.backend.communication.PlottingPreparationServiceRequest;
import com.example.backend.communication.PlottingPreparationServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class PlottingPreparationService {
    public PlottingPreparationServiceResponse callService(PlottingPreparationServiceRequest input) {
        String output = "Internal Service 3 output: " + input.getRequest();
        return new PlottingPreparationServiceResponse(output);
    }
}
