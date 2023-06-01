package com.example.backend.service;

import com.example.backend.communication.DataAnalysisServiceTonalityLeaningResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DataAnalysisService {

    public DataAnalysisServiceTonalityLeaningResponse.Probabilities predictTonalityAndLeaning(String urlString, String requestForDataAnalysisService) throws JSONException, IOException {
        StringBuilder response = connectAndGetResponse(requestForDataAnalysisService, urlString);
        JSONObject jsonObject = new JSONObject(response.toString());
        return (DataAnalysisServiceTonalityLeaningResponse.Probabilities) jsonObject.get("probabilities");
    }

    public String getWordcloud(String urlString, String requestForDataAnalysisService) throws IOException {
        StringBuilder response = connectAndGetResponse(requestForDataAnalysisService, urlString);
        return response.toString();
    }

    private StringBuilder connectAndGetResponse(String requestForDataAnalysisService, String urlString) throws IOException {
        HttpURLConnection connection;
        //Create connection
        URL url = new URL(urlString);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(requestForDataAnalysisService.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
        wr.writeBytes(requestForDataAnalysisService);
        wr.close();

        //Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();

        connection.disconnect();

        return response;
    }
}
