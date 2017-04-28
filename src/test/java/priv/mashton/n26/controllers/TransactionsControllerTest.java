package priv.mashton.n26.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import priv.mashton.n26.dtos.TransactionRequest;
import priv.mashton.n26.service.TransactionService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsControllerTest {

    @Mock
    TransactionService txService;

    @InjectMocks
    TransactionsController controller;

    @Test
    public void createTransactionReturnsBadRequestForNullRequest() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity response = controller.createTransaction(null);

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void createTransactionReturnsBadRequestForNullAmount() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;
        TransactionRequest request = new TransactionRequest(null, System.currentTimeMillis());

        //ACT
        ResponseEntity response = controller.createTransaction(null);

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void createTransactionReturnsBadRequestForNullTimestamp() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;
        TransactionRequest request = new TransactionRequest(12.3, null);

        //ACT
        ResponseEntity response = controller.createTransaction(null);

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void createTransactionReturnsBadRequestForZeroAmount() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;
        TransactionRequest request = new TransactionRequest(0.0, System.currentTimeMillis());

        //ACT
        ResponseEntity response = controller.createTransaction(request);

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void createTransactionReturnsBadRequestForNegativeAmount() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;
        TransactionRequest request = new TransactionRequest(-10.0, System.currentTimeMillis());

        //ACT
        ResponseEntity response = controller.createTransaction(request);

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void deleteTransactionsCallsClearOnService() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 200;

        //ACT
        ResponseEntity response = controller.deleteTransactions();

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
        verify(txService, times(1)).clearTransactions();
    }

}