package com.starlingbank.savingClient.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.starlingbank.savingClient.infrastructure.TransactionsResponseInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class GetAllTransactions {

    private String apiUrl;

    private RestTemplate restTemplate;

    private HttpEntity entity;

    /**
     * Constructor.
     *
     * @param accessToken
     * @param url
     */
    public GetAllTransactions(
            @Value("${accessToken}") String accessToken,
            @Value("${getTransactionsApiUrl}") String url
    ) {
        HttpHeaders headers;

        this.apiUrl = url;

        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        this.restTemplate = new RestTemplate(factory);
        this.restTemplate.setInterceptors(Collections.singletonList(new TransactionsResponseInterceptor()));

        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "saving-client");
        headers.add("Authorization", "Bearer " + accessToken);

        this.entity = new HttpEntity<>(headers);
    }

    /**
     * Method to GET all transactions.
     */
    public void getAll() {
        restTemplate.exchange(apiUrl, HttpMethod.GET, this.entity, JsonNode.class);
    }

}
