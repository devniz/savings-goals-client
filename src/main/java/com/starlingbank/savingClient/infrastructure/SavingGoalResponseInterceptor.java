package com.starlingbank.savingClient.infrastructure;

import com.starlingbank.savingClient.application.PutSavingAmountIntoGoal;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SavingGoalResponseInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest httpRequest,
            byte[] bytes,
            ClientHttpRequestExecution clientHttpRequestExecution
    ) throws IOException {
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpRequest);
            String savingAmount = requestWrapper.getHeaders().get("currentSavingAmount").get(0);
            String savingGoalUid = requestWrapper.getHeaders().get("currentSavingGoalUid").get(0);

            if (savingAmount != null && savingGoalUid != null) {
                PutSavingAmountIntoGoal save = new PutSavingAmountIntoGoal(savingAmount, savingGoalUid);
                save.transferSavingGoal();
            }
        }

        return response;
    }
}
