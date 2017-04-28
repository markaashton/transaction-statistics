package priv.mashton.n26.model;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    Queue<Transaction> concurrentTransactionQueue = new ConcurrentLinkedQueue<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        concurrentTransactionQueue.forEach(sb::append);
        return sb.toString();
    }

    public void clear() {
        concurrentTransactionQueue.clear();
    }

    public void addTransaction(double amount, long timestamp) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }

        Transaction tx = new Transaction(amount, timestamp);
        concurrentTransactionQueue.add(tx);
    }

    public List<Transaction> collectFilteredTransactions(Predicate<Transaction> pred) {
        return concurrentTransactionQueue.parallelStream().filter(pred).collect(Collectors.toList());
    }
}
