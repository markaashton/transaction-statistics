package priv.mashton.n26.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import priv.mashton.n26.model.Transaction;
import priv.mashton.n26.model.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class StatisticsRunnerTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    StatisticsRunner statisticsRunner;

    @Test
    public void testThatStatisticsRunnerUsesCorrectPredicateForFiltering() {
        //ARRANGE
        Class<Predicate<Transaction>> setClass = (Class<Predicate<Transaction>>)(Class)Predicate.class;
        ArgumentCaptor<Predicate<Transaction>> predicateCaptor = ArgumentCaptor.forClass(setClass);

        statisticsRunner.updateStatistics();
        verify(transactionRepository).collectFilteredTransactions(predicateCaptor.capture());
        Predicate<Transaction> predicate = predicateCaptor.getValue();

        List<Transaction> txList = new ArrayList<>();
        long timestamp = System.currentTimeMillis();
        long excludeTimestamp = timestamp - (65L*1000L);
        txList.add(new Transaction(15.0, timestamp));
        txList.add(new Transaction(15.0, timestamp));
        txList.add(new Transaction(25.0, excludeTimestamp));
        txList.add(new Transaction(15.0, timestamp));
        txList.add(new Transaction(25.0, excludeTimestamp));
        txList.add(new Transaction(25.0, excludeTimestamp));
        txList.add(new Transaction(15.0, timestamp));

        //ACT
        List<Transaction> collectedList = txList.parallelStream().filter(predicate).collect(Collectors.toList());

        //ASSERT
        assertThat(collectedList.size(), is(equalTo(4)));
        for (Transaction tx : collectedList) {
            assertThat(tx.getTimestamp(), is(equalTo(timestamp)));
            assertThat(tx.getAmount(), is(equalTo(15.0)));
        }
    }

}