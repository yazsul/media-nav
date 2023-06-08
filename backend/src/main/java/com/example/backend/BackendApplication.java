package com.example.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class BackendApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BackendApplication.class);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				/*Map<String,String> allowedOriginsList = new HashMap<>();
				ArrayList<String> origins = new ArrayList<>();
				
				origins.add("http://80.99.244.165:54321/**");
				origins.add("http://80.99.244.165:54322/**");
				origins.add("http://localhost:3000/**");
				origins.add("http://localhost:8080/**");
				
				for(String origin : origins) {	
					allowedOriginsList.put("/call-scraping-service", origin);
					allowedOriginsList.put("/predict-tonality", origin);
					allowedOriginsList.put("/predict-leaning", origin);
					allowedOriginsList.put("/wordcloud", origin);
					allowedOriginsList.put("/scrape-article-and-predict-tonality", origin);
					allowedOriginsList.put("/scrape-article-and-predict-leaning", origin);
					allowedOriginsList.put("/scrape-article-and-get-wordcloud", origin);
				}
				
				for(Map.Entry<String, String> entry : allowedOriginsList.entrySet()) {
					registry.addMapping(entry.getKey()).allowedOrigins(entry.getValue());
				}*/
				registry.addMapping("/**");
				
			}
		};
	}
}
