package com.starlingbank.savingClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starlingbank.savingClient.domain.entity.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Value("${accessToken}")
    private String accessToken;

    @Value("${getTransactionsApiUrl}")
    private String getTransactions;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Method to automatically fetch all transaction
     * And map the response to Transaction entity.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("user-agent", "saving-client");
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<JsonNode> response = restTemplate.exchange(getTransactions, HttpMethod.GET, entity, JsonNode.class);

        JsonNode map = response.getBody();
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        Transaction transactions = jsonObjectMapper.treeToValue(map, Transaction.class);

    }

}
