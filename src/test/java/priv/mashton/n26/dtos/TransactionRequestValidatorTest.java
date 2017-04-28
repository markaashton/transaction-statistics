package priv.mashton.n26.dtos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRequestValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void validatorThrowsIllegalArgumentExceptionWhenRequestIsNull() {
        TransactionRequestValidator.validate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatorThrowsIllegalArgumentExceptionWhenAmountIsNull() {
        //ARRANGE
        TransactionRequest request = new TransactionRequest(null, System.currentTimeMillis());

        //ACT
        TransactionRequestValidator.validate(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatorThrowsIllegalArgumentExceptionWhenTimestampIsNull() {
        //ARRANGE
        TransactionRequest request = new TransactionRequest(12.3, null);

        //ACT
        TransactionRequestValidator.validate(request);
    }

    @Test
    public void validatorThrowsNothingWhenRequestIsCorrect() {
        //ARRANGE
        TransactionRequest request = new TransactionRequest(12.3, System.currentTimeMillis());

        //ACT
        TransactionRequestValidator.validate(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatorThrowsIllegalArgumentExceptionWhenAmountLessThanZero() {
        //ARRANGE
        TransactionRequest request = new TransactionRequest(-10.0, System.currentTimeMillis());

        //ACT
        TransactionRequestValidator.validate(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatorThrowsIllegalArgumentExceptionWhenAmountZero() {
        //ARRANGE
        TransactionRequest request = new TransactionRequest(0.0, System.currentTimeMillis());

        //ACT
        TransactionRequestValidator.validate(request);
    }

}