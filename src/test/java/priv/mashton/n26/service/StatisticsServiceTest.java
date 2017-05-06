package priv.mashton.n26.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import priv.mashton.n26.statistics.StatisticsRepository;
import priv.mashton.n26.dtos.StatisticsResponse;
import priv.mashton.n26.model.TransactionStatistics;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    StatisticsRepository gatherer;

    @InjectMocks
    StatisticsService service;

    @Test
    public void getStatisticsGetsStatisticsFromGathererAndCorrectlyCopiesToDto() {
        //ARRANGE
        TransactionStatistics transactionStatistics = new TransactionStatistics();
        transactionStatistics.setCount(3L);
        transactionStatistics.setMax(15.0);
        transactionStatistics.setMin(1.0);
        transactionStatistics.setAvg(5.3);
        transactionStatistics.setSum(16.0);
        when(gatherer.getTransactionStatistics()).thenReturn(transactionStatistics);

        //ACT
        StatisticsResponse response = service.getTransactionStatistics();

        //ASSERT
        assertThat(response.getAvg(), is(equalTo(5.3)));
        assertThat(response.getMin(), is(equalTo(1.0)));
        assertThat(response.getMax(), is(equalTo(15.0)));
        assertThat(response.getSum(), is(equalTo(16.0)));
        assertThat(response.getCount(), is(equalTo(3L)));

    }

}