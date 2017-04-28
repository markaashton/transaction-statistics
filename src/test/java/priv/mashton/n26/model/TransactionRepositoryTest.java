package priv.mashton.n26.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRepositoryTest {

    class TransactionRepositoryWrapper extends TransactionRepository {
        int getCount() {
            return this.concurrentTransactionQueue.size();
        }

        Transaction getHeadElement() {
            return this.concurrentTransactionQueue.element();
        }
    }

    @Test
    public void testClearClears() {
        //ARRANGE
        TransactionRepositoryWrapper wrapper = new TransactionRepositoryWrapper();
        wrapper.addTransaction(10.0, 13L);

        //ACT
        wrapper.clear();

        //ASSERT
        assertThat(wrapper.getCount(), is(equalTo(0)));
    }

    @Test
    public void testAddAdds() {
        //ARRANGE
        TransactionRepositoryWrapper wrapper = new TransactionRepositoryWrapper();


        //ACT
        wrapper.addTransaction(15.0, 13L);

        //ASSERT
        assertThat(wrapper.getCount(), is(equalTo(1)));
        assertThat(wrapper.getHeadElement().getAmount(), is(equalTo(15.0)));
        assertThat(wrapper.getHeadElement().getTimestamp(), is(equalTo(13L)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTransactionThrowsIllegalArgumentExceptionOnZeroAmount() {
        //ARRANGE
        TransactionRepository repo = new TransactionRepository();

        //ACT
        repo.addTransaction(0, System.currentTimeMillis());

        //ASSERT
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTransactionThrowsIllegalArgumentExceptionOnNegativeAmount() {
        //ARRANGE
        TransactionRepository repo = new TransactionRepository();

        //ACT
        repo.addTransaction(-15.0, System.currentTimeMillis());

        //ASSERT
    }

}