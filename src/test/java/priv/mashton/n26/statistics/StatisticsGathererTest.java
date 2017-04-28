package priv.mashton.n26.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsGathererTest {

    @Mock
    StatisticsRunner statisticsRunner;

    @InjectMocks
    StatisticsGatherer statisticsGatherer;

    @Test
    public void cleanUpCallsStopOnTheRunner() {
        //ARRANGE

        //ACT
        statisticsGatherer.cleanup();

        //ASSERT
        verify(statisticsRunner,times(1)).stop();
    }

    @Test
    public void testSomething() {
        //ARRANGE

        //ACT
        statisticsGatherer.getTransactionStatistics();

        //ASSERT
        verify(statisticsRunner, times(1)).getTransactionStatistics();
    }

}