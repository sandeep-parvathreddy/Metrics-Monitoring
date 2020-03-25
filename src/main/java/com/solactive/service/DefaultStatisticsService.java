package com.solactive.service;

import com.solactive.exception.DataAccessException;
import com.solactive.repository.StatisticsRepository;
import com.solactive.dao.StatisticsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandeepreddy on 23/03/20.
 */
@Service
public class DefaultStatisticsService implements StatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    StatisticsRepository statisticsRepository;


    @Override
    public StatisticsDAO getStatistics() {
        return getStatistics(null);
    }

    @Override
    public StatisticsDAO getStatistics(String instrumentName) {
        StatisticsDAO statisticsDAO = null;
        try {
            statisticsDAO = statisticsRepository.get(60,instrumentName);
        }
        catch (DataAccessException e){
            LOG.warn("Error occurred in building the statistics for instrumentName : {}",instrumentName,e);
        }
        return statisticsDAO;
    }

}
