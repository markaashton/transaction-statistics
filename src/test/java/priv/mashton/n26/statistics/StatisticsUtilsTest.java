package priv.mashton.n26.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import priv.mashton.n26.model.Transaction;
import priv.mashton.n26.model.TransactionStatistics;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsUtilsTest {

    @Test
    public void calculateStatisticsReturnsZeroValuesForNullList() {
        //ARRANGE & ACT
        TransactionStatistics statistics = StatisticsUtils.calculateStatistics(null);

        //ASSERT
        assertThat(statistics.getCount(), is(equalTo(0L)));
        assertThat(statistics.getSum(), is(equalTo(0.0)));
        assertThat(statistics.getMin(), is(equalTo(0.0)));
        assertThat(statistics.getMax(), is(equalTo(0.0)));
        assertThat(statistics.getAvg(), is(equalTo(0.0)));
    }

    @Test
    public void calculateStatisticsReturnsZeroValuesForEmptyList() {
        //ARRANGE
        List<Transaction> txList = new ArrayList<>();

        //ACT
        TransactionStatistics statistics = StatisticsUtils.calculateStatistics(txList);

        //ASSERT
        assertThat(statistics.getCount(), is(equalTo(0L)));
        assertThat(statistics.getSum(), is(equalTo(0.0)));
        assertThat(statistics.getMin(), is(equalTo(0.0)));
        assertThat(statistics.getMax(), is(equalTo(0.0)));
        assertThat(statistics.getAvg(), is(equalTo(0.0)));
    }

    @Test
    public void calculateStatisticsReturnsCorrectValuesForOneElementList() {
        //ARRANGE
        List<Transaction> txList = new ArrayList<>();
        long timestamp = System.currentTimeMillis();
        txList.add(new Transaction(15.0, timestamp));

        //ACT
        TransactionStatistics statistics = StatisticsUtils.calculateStatistics(txList);

        //ASSERT
        assertThat(statistics.getCount(), is(equalTo(1L)));
        assertThat(statistics.getSum(), is(equalTo(15.0)));
        assertThat(statistics.getMin(), is(equalTo(15.0)));
        assertThat(statistics.getMax(), is(equalTo(15.0)));
        assertThat(statistics.getAvg(), is(equalTo(15.0)));
    }

    @Test
    public void calculateStatisticsReturnsCorrectValuesForPopulatedList() {
        //ARRANGE
        List<Transaction> txList = new ArrayList<>();
        long timestamp = System.currentTimeMillis();

        txList.add(new Transaction(25.0, timestamp));
        txList.add(new Transaction(15.0, timestamp));
        txList.add(new Transaction(15.0, timestamp));
        txList.add(new Transaction(35.0, timestamp));
        txList.add(new Transaction(45.0, timestamp));
        txList.add(new Transaction(20.0, timestamp));
        txList.add(new Transaction(100.75, timestamp));
        txList.add(new Transaction(100.25, timestamp));
        txList.add(new Transaction(175.75, timestamp));

        //ACT
        TransactionStatistics statistics = StatisticsUtils.calculateStatistics(txList);

        //ASSERT
        assertThat(statistics.getCount(), is(equalTo(9L)));
        assertThat(statistics.getSum(), is(equalTo(531.75)));
        assertThat(statistics.getMin(), is(equalTo(15.0)));
        assertThat(statistics.getMax(), is(equalTo(175.75)));
        assertThat(statistics.getAvg(), is(equalTo(59.08)));
    }

}