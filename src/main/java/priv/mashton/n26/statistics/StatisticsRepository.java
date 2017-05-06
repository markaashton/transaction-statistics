package priv.mashton.n26.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import priv.mashton.n26.model.TransactionStatistics;

@Repository
public class StatisticsRepository implements Statistics {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private TransactionStatistics transactionStatistics = new TransactionStatistics();

    synchronized public TransactionStatistics getTransactionStatistics() {
        return transactionStatistics.makeCopy();
    }

    synchronized void setTransactionStatistics(TransactionStatistics statistics) {
        this.transactionStatistics = statistics;
    }

}
