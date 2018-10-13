package com.starlingbank.savingClient;

import com.starlingbank.savingClient.application.GetAllTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private GetAllTransactions getAllTransactions;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Method to automatically fetch all transactions.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        this.getAllTransactions.getAll();
    }

}
