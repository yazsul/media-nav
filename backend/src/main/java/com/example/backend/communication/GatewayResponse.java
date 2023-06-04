package com.example.backend.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GatewayResponse {
    private String responseFromScrappingService;
    private String responseFromDataAnalysisService;
}
