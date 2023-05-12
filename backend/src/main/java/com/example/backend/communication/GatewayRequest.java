package com.example.backend.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GatewayRequest {
    private String requestForScrappingService;
    private String requestForDataAnalysisService;
}
