package com.example.backend.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

@Service
public class DataAnalysisService {
    private static final String URL = "http://localhost:8000/";
    private static final String TONALITY_ENDPOINT = "predict_tonality";
    private static final String LEANING_ENDPOINT = "predict_leaning";
    private static final String WORDCLOUD_ENDPOINT = "wordcloud";

    public String predictTonality(String requestForDataAnalysisService) throws IOException {
        return runDataAnalysisRequest(TONALITY_ENDPOINT, requestForDataAnalysisService);
    }

    public String predictLeaning(String requestForDataAnalysisService) throws IOException {
        return runDataAnalysisRequest(LEANING_ENDPOINT, requestForDataAnalysisService);

    }

    public String getWordCloud(String requestForDataAnalysisService) throws IOException {
        return runDataAnalysisRequest(WORDCLOUD_ENDPOINT, requestForDataAnalysisService);
    }

    private String runDataAnalysisRequest(final String endpoint, final String paragraphToAnalyze){
        HttpRequest request = buildHttpRequest(endpoint, paragraphToAnalyze);
        HttpResponse<String> response = getHttpResponse(request);
        return response.body();
    }

    private HttpRequest buildHttpRequest(final String endpoint, final String paragraphToAnalyze){
        return HttpRequest.newBuilder()
                .uri(URI.create(URL + endpoint))
                .method("POST", HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"text\": \"" + paragraphToAnalyze + "\"\n" +
                        "}"))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    private HttpResponse getHttpResponse(final HttpRequest request){
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
