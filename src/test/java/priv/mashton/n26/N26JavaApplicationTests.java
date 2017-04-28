package priv.mashton.n26;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import priv.mashton.n26.dtos.StatisticsResponse;
import priv.mashton.n26.dtos.TransactionRequest;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class N26JavaApplicationTests {

    @Autowired
    private TestRestTemplate rest;

    @Value("${app.updateperiodms ?: 1000}")
    long updatePeriodMs;

    @Before
    public void before() {
        rest.delete("http://localhost:8080/transactions");
    }

    @Test
    public void getStatisticsReturnsInitialStats() {
        //ARRANGE
        final String BASE_URL = "http://localhost:8080/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/statistics")
                .build()
                .toUri();

        //ACT
        ResponseEntity<StatisticsResponse> response = rest.getForEntity(targetUrl, StatisticsResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        StatisticsResponse responseBody = response.getBody();

        assertNotNull(responseBody);
        assertThat(responseBody.getAvg(), is(equalTo(0.0)));
        assertThat(responseBody.getSum(), is(equalTo(0.0)));
        assertThat(responseBody.getMin(), is(equalTo(0.0)));
        assertThat(responseBody.getMax(), is(equalTo(0.0)));
        assertThat(responseBody.getCount(), is(equalTo(0L)));
    }

    @Test
    public void getStatisticsReturnsCorrectValues() throws Exception {
        //ARRANGE
        final String BASE_URL = "http://localhost:8080/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/statistics")
                .build()
                .toUri();

        createTestData();
        Thread.sleep(updatePeriodMs);

        //ACT
        ResponseEntity<StatisticsResponse> response = rest.getForEntity(targetUrl, StatisticsResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        StatisticsResponse responseBody = response.getBody();

        assertNotNull(responseBody);
        assertThat(responseBody.getAvg(), is(equalTo(16.0)));
        assertThat(responseBody.getSum(), is(equalTo(80.0)));
        assertThat(responseBody.getMin(), is(equalTo(10.0)));
        assertThat(responseBody.getMax(), is(equalTo(30.0)));
        assertThat(responseBody.getCount(), is(equalTo(5L)));

    }

    @Test
    public void createTransactionReturnsNoContentOnSuccess() {
        //ARRANGE
        String REQUEST_URL = "http://localhost:8080/transactions";

        TransactionRequest request = new TransactionRequest(12.3, System.currentTimeMillis());

        //ACT
        ResponseEntity response = rest.postForEntity(REQUEST_URL, request, Void.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));
    }

    @Test
    public void createTransactionReturnsBadRequestForZeroAmount() {
        //ARRANGE
        String REQUEST_URL = "http://localhost:8080/transactions";

        TransactionRequest request = new TransactionRequest(0.0, System.currentTimeMillis());

        //ACT
        ResponseEntity response = rest.postForEntity(REQUEST_URL, request, Void.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void createTransactionReturnsBadRequestForNegativeAmount() {
        //ARRANGE
        String REQUEST_URL = "http://localhost:8080/transactions";

        TransactionRequest request = new TransactionRequest(-10.0, System.currentTimeMillis());

        //ACT
        ResponseEntity response = rest.postForEntity(REQUEST_URL, request, Void.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void createTransactionReturnsBadRequestForNullAmount() {
        //ARRANGE
        String REQUEST_URL = "http://localhost:8080/transactions";

        TransactionRequest request = new TransactionRequest(null, System.currentTimeMillis());

        //ACT
        ResponseEntity response = rest.postForEntity(REQUEST_URL, request, Void.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void createTransactionReturnsBadRequestForNullTimestamp() {
        //ARRANGE
        String REQUEST_URL = "http://localhost:8080/transactions";

        TransactionRequest request = new TransactionRequest(10.0, null);

        //ACT
        ResponseEntity response = rest.postForEntity(REQUEST_URL, request, Void.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    private void createTestData() {
        String REQUEST_URL = "http://localhost:8080/transactions";

        long currentTimeStampMs = System.currentTimeMillis();
        long excludeTimeStampMs = currentTimeStampMs - (65L * 1000L);

        TransactionRequest request = new TransactionRequest(10.0, currentTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);

        request.setAmount(30.0);
        request.setTimestamp(currentTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);

        request.setAmount(10.0);
        request.setTimestamp(currentTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);

        request.setAmount(9.0);
        request.setTimestamp(excludeTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);

        request.setAmount(20.0);
        request.setTimestamp(currentTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);

        request.setAmount(9.0);
        request.setTimestamp(excludeTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);

        request.setAmount(10.0);
        request.setTimestamp(currentTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);

        request.setAmount(9.0);
        request.setTimestamp(excludeTimeStampMs);
        rest.postForEntity(REQUEST_URL, request, Void.class);
    }

}
