package priv.mashton.n26.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.mashton.n26.statistics.StatisticsRepository;
import priv.mashton.n26.dtos.StatisticsResponse;
import priv.mashton.n26.model.TransactionStatistics;

@Service
public class StatisticsService {

    @Autowired
    StatisticsRepository statisticsRepository;

    public StatisticsResponse getTransactionStatistics() {
        TransactionStatistics statistics = statisticsRepository.getTransactionStatistics();

        StatisticsResponse statisticsResponse = new StatisticsResponse();

        statisticsResponse.setSum(statistics.getSum());
        statisticsResponse.setAvg(statistics.getAvg());
        statisticsResponse.setMin(statistics.getMin());
        statisticsResponse.setMax(statistics.getMax());
        statisticsResponse.setCount(statistics.getCount());

        return statisticsResponse;
    }

}
