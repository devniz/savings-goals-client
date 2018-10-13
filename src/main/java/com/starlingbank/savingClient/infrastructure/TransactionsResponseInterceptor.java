package com.starlingbank.savingClient.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starlingbank.savingClient.application.PutNewSavingGoal;
import com.starlingbank.savingClient.domain.entity.Transaction;
import com.starlingbank.savingClient.ui.RoundUpController;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Http interceptor class when we get all Transactions from the API.
 */
public class TransactionsResponseInterceptor implements ClientHttpRequestInterceptor {

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
        String body = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());

        RoundUpController roundUpController = new RoundUpController();
        PutNewSavingGoal putNewSavingGoal = new PutNewSavingGoal();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(body);
        JsonNode embedded = jsonNode.get("_embedded");
        JsonNode getTransactions = embedded.get("transactions");

        if (getTransactions != null) {
            Transaction[] transactions = mapper.treeToValue(getTransactions, Transaction[].class);
            List<Transaction> transactionList = Arrays.asList(transactions);
            BigDecimal totalSaving = roundUpController.calculateRoundUp(transactionList);

            // There is no point to save 0.00 £.
            if (totalSaving != null && totalSaving.compareTo(BigDecimal.valueOf(0.00)) != 0) {
                putNewSavingGoal.createSavingGoal(totalSaving);
            }
        }

        return response;
    }

}
