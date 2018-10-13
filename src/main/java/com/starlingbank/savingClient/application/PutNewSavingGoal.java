package com.starlingbank.savingClient.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.starlingbank.savingClient.infrastructure.SavingGoalResponseInterceptor;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Service
public class PutNewSavingGoal {

    private String newGoalApiUrl;

    private RestTemplate restTemplate;

    private HttpHeaders headers;

    private String currentSavingGoalUid;

    public PutNewSavingGoal() {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        this.restTemplate = new RestTemplate(factory);
        this.restTemplate.setInterceptors(Collections.singletonList(new SavingGoalResponseInterceptor()));

        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.add("user-agent", "saving-client");
        this.headers.add("Authorization", "Bearer " + "Tort6mt67fJWxMRkRJw9wxrarv8gDkXxrUcCBBzMlXk5emn4GJND65glGog5BYnZ");

        this.currentSavingGoalUid = UUID.randomUUID().toString();
        this.newGoalApiUrl = "https://api-sandbox.starlingbank.com/api/v1/savings-goals/" + this.currentSavingGoalUid;

        this.headers.set("currentSavingGoalUid", this.currentSavingGoalUid);
    }

    /**
     * Create a new saving goal with the generated UUID
     * Then transfer the calculated savings.
     *
     * @param saving
     */
    public void createSavingGoal(BigDecimal saving) {
        this.headers.set("currentSavingAmount", saving.toString());
        String bodyRequest = "{\n  \"name\": \"Trip to Paris\",\n  \"currency\": \"GBP\",\n  \"target\": {\n\"currency\": \"GBP\",\n\"minorUnits\": 11223344\n  },\n\"base64EncodedPhoto\": \"string\"\n}";
        HttpEntity<String> request = new HttpEntity<>(bodyRequest, this.headers);
        restTemplate.exchange(newGoalApiUrl, HttpMethod.PUT, request, JsonNode.class);
    }

}
