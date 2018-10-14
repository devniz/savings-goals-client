package com.starlingbank.savingClient.ui

import com.starlingbank.savingClient.domain.entity.Transaction
import spock.lang.Specification

class RoundUpControllerSpec extends Specification {

    private RoundUpController roundUpController = new RoundUpController()

    def "It should calculate the round up without errors"() {
        given:
        Transaction t1 = new Transaction()
        Transaction t2 = new Transaction()
        Transaction t3 = new Transaction()
        t1.setAmount(4.35)
        t2.setAmount(5.20)
        t3.setAmount(0.87)
        List<Transaction> transactions= new ArrayList<>()
        transactions.add(t1)
        transactions.add(t2)
        transactions.add(t3)

        and:
        def result = roundUpController.calculateRoundUp(transactions)

        expect:
        result == 1.58
    }

}