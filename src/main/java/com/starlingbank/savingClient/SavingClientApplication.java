package com.starlingbank.savingClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SavingClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingClientApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();
	}
}
