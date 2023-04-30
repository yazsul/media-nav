package com.example.backend.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileParameters {
    private final String CONFIG_PROPERTIES_PATH = "src\\main\\resources\\application.properties";
    private final String DATA_ANALYSIS_SERVICE_MAIN_SCRIPT_PATH = "data.analysis.main.script.path";
    private final String DATA_ANALYSIS_SERVICE_INPUT_DIR_PATH = "data.analysis.input.directory.path";
    private final String DATA_ANALYSIS_SERVICE_OUTPUT_DIR_PATH = "data.analysis.output.directory.path";
    private String dataAnalysisServiceMainScriptRelativePath;
    private String dataAnalysisServiceInputRelativePath;
    private String dataAnalysisServiceOutputRelativePath;
    private Properties properties = new Properties();

    public PropertiesFileParameters() {
        try (InputStream input = new FileInputStream(CONFIG_PROPERTIES_PATH)) {
            properties.load(input);
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void init() {
        dataAnalysisServiceMainScriptRelativePath = properties.getProperty(DATA_ANALYSIS_SERVICE_MAIN_SCRIPT_PATH);
        dataAnalysisServiceInputRelativePath = properties.getProperty(DATA_ANALYSIS_SERVICE_INPUT_DIR_PATH);
        dataAnalysisServiceOutputRelativePath = properties.getProperty(DATA_ANALYSIS_SERVICE_OUTPUT_DIR_PATH);
    }

    public String getDataAnalysisServiceMainScriptRelativePath() {
        return dataAnalysisServiceMainScriptRelativePath;
    }

    public String getDataAnalysisServiceInputRelativePath() {
        return dataAnalysisServiceInputRelativePath;
    }

    public String getDataAnalysisServiceOutputRelativePath() {
        return dataAnalysisServiceOutputRelativePath;
    }
}
