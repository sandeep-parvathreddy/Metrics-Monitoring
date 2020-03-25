package com.solactive.repository.impl;

import com.solactive.exception.DataAccessException;
import com.solactive.repository.StatisticsRepository;
import com.solactive.dataStore.TicksDataStore;
import com.solactive.dao.StatisticsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeepreddy on 22/03/20.
 */
@Service
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsRepositoryImpl.class);

    @Autowired
    TicksDataStore ticksDataStore;

    @Override
    public StatisticsDAO get(long numOfSeconds) throws DataAccessException {
        return get(numOfSeconds,null);

    }


    @Override
    public StatisticsDAO get(long numOfSeconds, String instrument) throws DataAccessException {
        StatisticsDAO consolidatedStatistics = new StatisticsDAO();
        long currentTime = System.currentTimeMillis()/1000;
        long count = 0;
        try {
            while (count < numOfSeconds) {
                StatisticsDAO statisticsDAO = ticksDataStore.getStatisticsPerInstrumentByTime(instrument, currentTime - count);
                if (statisticsDAO!=null && statisticsDAO.getCount() != 0) {
                    appendStatistics(consolidatedStatistics,statisticsDAO);
                }
                count++;
            }
        }
        catch (Exception e){
            LOG.error("Error occurred in fetching the statistics ",e);
            throw new DataAccessException(e.getMessage());
        }
        return consolidatedStatistics;
    }



    private void appendStatistics(StatisticsDAO consolidatedStatistics, StatisticsDAO statisticsDAO) {
        consolidatedStatistics.setSum(consolidatedStatistics.getSum() + statisticsDAO.getSum());
        consolidatedStatistics.setMax(Math.max(consolidatedStatistics.getMax(), statisticsDAO.getMax()));
        consolidatedStatistics.setCount(consolidatedStatistics.getCount() + statisticsDAO.getCount());
        consolidatedStatistics.setMin(Math.min(consolidatedStatistics.getMin(), statisticsDAO.getMin()));
    }
}
