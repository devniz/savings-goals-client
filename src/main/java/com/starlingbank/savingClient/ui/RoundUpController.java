package com.starlingbank.savingClient.ui;

import com.starlingbank.savingClient.domain.entity.Transaction;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoundUpController {

    /**
     * Calculate round-up of all fetched transactions.
     *
     * @param transactions
     */
    public BigDecimal calculateRoundUp(List<Transaction> transactions) {
        List<BigDecimal> values = new ArrayList<>();

        for (Transaction t : transactions) {
            String clearAmount = t.getAmount().toString().replace("-", "");
            BigDecimal roundedAmount = new BigDecimal(clearAmount).setScale(0, BigDecimal.ROUND_UP);
            BigDecimal diff = roundedAmount.subtract(new BigDecimal(clearAmount));
            values.add(diff);
        }
        return values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
