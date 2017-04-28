package priv.mashton.n26.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.mashton.n26.statistics.StatisticsGatherer;
import priv.mashton.n26.dtos.StatisticsResponse;
import priv.mashton.n26.model.TransactionStatistics;

@Service
public class StatisticsService {

    @Autowired
    StatisticsGatherer gatherer;

    public StatisticsResponse getTransactionStatistics() {
        TransactionStatistics statistics = gatherer.getTransactionStatistics();

        StatisticsResponse statisticsResponse = new StatisticsResponse();

        statisticsResponse.setSum(statistics.getSum());
        statisticsResponse.setAvg(statistics.getAvg());
        statisticsResponse.setMin(statistics.getMin());
        statisticsResponse.setMax(statistics.getMax());
        statisticsResponse.setCount(statistics.getCount());

        return statisticsResponse;
    }

}
