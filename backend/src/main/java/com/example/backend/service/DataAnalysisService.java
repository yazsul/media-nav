package com.example.backend.service;

import com.example.backend.communication.DataAnalysisServiceRequest;
import com.example.backend.communication.DataAnalysisServiceResponse;
import com.example.backend.util.Command;
import com.example.backend.util.PropertiesFileParameters;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class DataAnalysisService {
    public DataAnalysisServiceResponse callService(DataAnalysisServiceRequest request) {
        executeDataAnalysisMainScript();
        String response = "Internal Service 2 response";
        return new DataAnalysisServiceResponse(response);
    }

    private void executeDataAnalysisMainScript() {
        String response = new Command().execute(getCommandToRunDataAnalysisMainScript());
        System.out.println(response);
    }

    private String getDataAnalysisMainScriptAbsolutePath() {
        String path = new File("").getAbsolutePath() + new PropertiesFileParameters().getDataAnalysisServiceMainScriptRelativePath();
        return path;
    }

    private String getDataAnalysisMainScriptInputDirAbsolutePath() {
        String path = new File("").getAbsolutePath() + new PropertiesFileParameters().getDataAnalysisServiceInputRelativePath();
        return path;
    }

    private String getDataAnalysisMainScriptOutputDirAbsolutePath() {
        String path = new File("").getAbsolutePath() + new PropertiesFileParameters().getDataAnalysisServiceOutputRelativePath();
        return path;
    }

    private String getCommandToRunDataAnalysisMainScript() {
        PropertiesFileParameters properties = new PropertiesFileParameters();
        String mainScriptPath = getDataAnalysisMainScriptAbsolutePath();
        String commandToRunDataAnalysisMainScript = "python " + mainScriptPath + " " + getDataAnalysisMainScriptInputDirAbsolutePath()
                + " " + getDataAnalysisMainScriptOutputDirAbsolutePath();
        return commandToRunDataAnalysisMainScript;
    }
}
