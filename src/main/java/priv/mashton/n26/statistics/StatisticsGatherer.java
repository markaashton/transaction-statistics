package priv.mashton.n26.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.mashton.n26.model.TransactionStatistics;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class StatisticsGatherer implements Statistics {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StatisticsRunner statisticsRunner;

    public TransactionStatistics getTransactionStatistics() {
        return statisticsRunner.getTransactionStatistics();
    }

    @PostConstruct
    void initialise() throws Exception {
        logger.info("statistics initialised");
        Thread thread = new Thread(statisticsRunner);
        thread.start();
    }

    @PreDestroy
    void cleanup() {
        logger.info("statistics destroyed");
        statisticsRunner.stop();
    }

}
