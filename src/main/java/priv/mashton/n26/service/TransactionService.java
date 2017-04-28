package priv.mashton.n26.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.mashton.n26.model.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void addTransaction(double amount, long timestamp) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }

        transactionRepository.addTransaction(amount, timestamp);
    }

    public void clearTransactions() {
        transactionRepository.clear();
    }

}
