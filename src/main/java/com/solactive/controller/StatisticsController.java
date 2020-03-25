package com.solactive.controller;

import com.solactive.controller.mapper.StatisticsMapper;
import com.solactive.model.StatisticsDTO;
import com.solactive.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sandeepreddy on 24/03/20.
 */
@RestController
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/statistics")
    @ResponseBody
    public StatisticsDTO getStatistics(){
        return StatisticsMapper.makeStatistics(statisticsService.getStatistics());
    }


    @GetMapping("/statistics/{instrument_identifier:.+}")
    @ResponseBody
    public StatisticsDTO getStatistics(@PathVariable("instrument_identifier") String instrumentIdentifier){
        return StatisticsMapper.makeStatistics(statisticsService.getStatistics(instrumentIdentifier));
    }

}
