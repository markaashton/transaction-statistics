package priv.mashton.n26.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import priv.mashton.n26.model.Transaction;
import priv.mashton.n26.model.TransactionStatistics;
import priv.mashton.n26.model.TransactionRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.function.Predicate;

@Component
class StatisticsRunner implements Runnable {

    @Value("${app.updateperiodms ?: 1000}")
    long updatePeriodMs;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    private boolean isRunning = false;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Thread t;

    public StatisticsRunner() {
        t = new Thread(this, "statistics thread");
    }

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

    void updateStatistics() {
        long sixtySecondsEarlierMs = System.currentTimeMillis() - (60L * 1000L);
        Predicate<Transaction> lastSixtySecondsPred = p -> p.getTimestamp() >= sixtySecondsEarlierMs;
        List<Transaction> filteredTransactions = transactionRepository.collectFilteredTransactions(lastSixtySecondsPred);

        TransactionStatistics updatedStatistics = StatisticsUtils.calculateStatistics(filteredTransactions);

        statisticsRepository.setTransactionStatistics(updatedStatistics);
    }

    @PostConstruct
    void initialise() throws Exception {
        logger.info("statistics initialised");
        t.start();
    }

    @PreDestroy
    void cleanup() {
        logger.info("statistics destroyed");
        this.stop();
    }

}
