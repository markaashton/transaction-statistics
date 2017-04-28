package priv.mashton.n26.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.mashton.n26.dtos.StatisticsResponse;
import priv.mashton.n26.service.StatisticsService;

@Controller
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces="application/json" )
    @ResponseBody
    public ResponseEntity<StatisticsResponse> getStatistics() {
        return ResponseEntity.ok(statisticsService.getTransactionStatistics());
        }

}
