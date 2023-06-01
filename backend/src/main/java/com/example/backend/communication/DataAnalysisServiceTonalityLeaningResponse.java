package com.example.backend.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataAnalysisServiceTonalityLeaningResponse {

    private Probabilities probabilities;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Probabilities {

        private String negative;

        private String neutral;

        private String positive;
    }
}
