package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DigitalLibraryApplication {

	
	@Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000); 
        requestFactory.setReadTimeout(5000);   
        return new RestTemplate(requestFactory);
    }

	public static void main(String[] args) {

		System.out.println("Starting Spring Boot Application...");
		SpringApplication.run(DigitalLibraryApplication.class, args);
	}
}
