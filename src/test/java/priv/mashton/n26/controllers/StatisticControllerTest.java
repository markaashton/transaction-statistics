package priv.mashton.n26.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import priv.mashton.n26.dtos.StatisticsResponse;
import priv.mashton.n26.service.StatisticsService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticControllerTest {

    @Mock
    StatisticsService statService;

    @InjectMocks
    StatisticsController controller;

    @Test
    public void testSomething() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 200;

        StatisticsResponse expectedResponse = new StatisticsResponse();
        expectedResponse.setCount(5L);
        expectedResponse.setMin(1.0);
        expectedResponse.setMin(10.0);
        expectedResponse.setSum(10.0);
        expectedResponse.setAvg(2.0);

        when(statService.getTransactionStatistics()).thenReturn(expectedResponse);

        //ACT
        ResponseEntity<StatisticsResponse> response = controller.getStatistics();

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
        assertThat(response.getBody().getAvg(), is(equalTo(expectedResponse.getAvg())));
        assertThat(response.getBody().getMin(), is(equalTo(expectedResponse.getMin())));
        assertThat(response.getBody().getMax(), is(equalTo(expectedResponse.getMax())));
        assertThat(response.getBody().getSum(), is(equalTo(expectedResponse.getSum())));
        assertThat(response.getBody().getCount(), is(equalTo(expectedResponse.getCount())));

    }


}