package com.starlingbank.savingClient.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.starlingbank.savingClient.ui.RoundUpController;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Http interceptor class when we get all Transactions from the API.
 */
public class TransactionsResponseInterceptor implements ClientHttpRequestInterceptor {


    private RoundUpController roundUpController;

    public TransactionsResponseInterceptor() {
        roundUpController = new RoundUpController();
    }

    /**
     * This will be triggered when the response from starling API come's back
     *
     * @param httpRequest
     * @param bytes
     * @param clientHttpRequestExecution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(
            HttpRequest httpRequest,
            byte[] bytes,
            ClientHttpRequestExecution clientHttpRequestExecution
    ) throws IOException {
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Map<String, Object> transactionsMap = mapper.convertValue(response.getBody(), Map.class);
        this.roundUpController.calculateRoundUp(transactionsMap);

        return response;
    }

}
