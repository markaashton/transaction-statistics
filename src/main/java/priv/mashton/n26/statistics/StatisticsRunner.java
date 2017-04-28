package priv.mashton.n26.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import priv.mashton.n26.model.Transaction;
import priv.mashton.n26.model.TransactionStatistics;
import priv.mashton.n26.model.TransactionRepository;

import java.util.List;
import java.util.function.Predicate;

@Component
class StatisticsRunner implements Runnable {

    @Autowired
    TransactionRepository transactionRepository;

    private boolean isRunning = false;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private TransactionStatistics transactionStatistics = new TransactionStatistics();

    @Value("${app.updateperiodms ?: 1000}")
    long updatePeriodMs;

    @Override
    public void run() {
        isRunning = true;
        logger.info("statistics thread is running");
        try {
            while (isRunning) {
                updateStatistics();
                Thread.sleep(updatePeriodMs);
            }
        } catch (InterruptedException ex) {
            logger.info("statistics thread interrupted");
        }

        logger.info("statistics thread exiting");
    }

    public void stop() {
        isRunning = false;
    }

    public TransactionStatistics getTransactionStatistics() {
        synchronized (this) {
            return transactionStatistics.makeCopy();
        }
    }

    void updateStatistics() {
        long sixtySecondsEarlierMs = System.currentTimeMillis() - (60L * 1000L);
        Predicate<Transaction> lastSixtySecondsPred = p -> p.getTimestamp() >= sixtySecondsEarlierMs;
        List<Transaction> filteredTransactions = transactionRepository.collectFilteredTransactions(lastSixtySecondsPred);

        TransactionStatistics updatedStatistics = StatisticsUtils.calculateStatistics(filteredTransactions);

        synchronized (this)
        {
            transactionStatistics.setAvg(updatedStatistics.getAvg());
            transactionStatistics.setCount(updatedStatistics.getCount());
            transactionStatistics.setMax(updatedStatistics.getMax());
            transactionStatistics.setMin(updatedStatistics.getMin());
            transactionStatistics.setSum(updatedStatistics.getSum());
        }
    }

}
