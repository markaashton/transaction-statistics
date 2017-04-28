package priv.mashton.n26.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.mashton.n26.dtos.StatisticsResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ServiceIntegrationTest {

    @Autowired
    TransactionService txService;

    @Autowired
    StatisticsService statService;

    @Before
    public void setup() {
        txService.clearTransactions();
    }

    @Value("${app.updateperiodms ?: 1000}")
    long updatePeriodMs;

    @Test
    public void testThatAddingATransactionIsReflectedInStatistics() throws Exception {
        //ARRANGE
        txService.addTransaction(1.0, System.currentTimeMillis());
        Thread.sleep(updatePeriodMs);

        //ACT
        StatisticsResponse response = statService.getTransactionStatistics();

        //ASSERT
        assertThat(response.getCount(), is(equalTo(1L)));
    }

    @Test
    public void testThatCorrectStatisticsAreReceived() throws Exception {
        //ARRANGE
        txService.addTransaction(10.0, System.currentTimeMillis());
        txService.addTransaction(15.0, System.currentTimeMillis());
        txService.addTransaction(25.0, System.currentTimeMillis());
        txService.addTransaction(35.0, System.currentTimeMillis());
        txService.addTransaction(5.0, System.currentTimeMillis());
        txService.addTransaction(100.0, System.currentTimeMillis());
        txService.addTransaction(13.0, System.currentTimeMillis());
        txService.addTransaction(1.0, System.currentTimeMillis());
        Thread.sleep(updatePeriodMs);

        //ACT
        StatisticsResponse response = statService.getTransactionStatistics();

        //ASSERT
        assertThat(response.getCount(), is(equalTo(8L)));
        assertThat(response.getSum(), is(equalTo(204.0)));
        assertThat(response.getAvg(), is(equalTo(25.5)));
        assertThat(response.getMin(), is(equalTo(1.0)));
        assertThat(response.getMax(), is(equalTo(100.0)));

    }


}