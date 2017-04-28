package priv.mashton.n26.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import priv.mashton.n26.model.TransactionRepository;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionService service;

    @Test(expected = IllegalArgumentException.class)
    public void addTransactionThrowsIllegalArgumentExceptionForZeroAmount() {
        service.addTransaction(0.0, System.currentTimeMillis()) ;
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTransactionThrowsIllegalArgumentExceptionForNegativeAmount() {
        service.addTransaction(-10.0, System.currentTimeMillis()) ;
    }

    @Test
    public void addTransactionsCallsAddOnRepository() {
        //ARRANGE
        double amount = 10.0;
        long timestamp = System.currentTimeMillis();

        //ACT
        service.addTransaction(amount, timestamp) ;

        //ASSERT
        verify(transactionRepository, times(1)).addTransaction(eq(10.0), eq(timestamp));
    }

    @Test
    public void clearCallsClearOnRepository() {
        //ARRANGE

        //ACT
        service.clearTransactions();

        //ASSERT
        verify(transactionRepository, times(1)).clear();
    }




}