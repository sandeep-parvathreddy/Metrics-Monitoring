package com.solactive.repository.impl;

import com.solactive.repository.TickRepository;
import com.solactive.dataStore.TicksDataStore;
import com.solactive.dao.StatisticsDAO;
import com.solactive.dao.TickDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sandeepreddy on 22/03/20.
 */
@Service
public class TickRepositoryImpl implements TickRepository {


    @Autowired
    TicksDataStore ticksDataStore;

    /*
    * adding tick information to data store
    * */
    @Override
    synchronized public void addTick(TickDAO tickDAO) {
        StatisticsDAO statisticsDAOPerTick = ticksDataStore.getStatisticsByTime(tickDAO.getTimestamp());
        appendTicksToInstrumentStatistics(statisticsDAOPerTick, tickDAO);
        ticksDataStore.addStatisticsForTick(tickDAO, statisticsDAOPerTick);

        StatisticsDAO statisticsDAO = ticksDataStore.getStatisticsPerInstrumentByTime(tickDAO.getInstrument(), tickDAO.getTimestamp());
        appendTicksToInstrumentStatistics(statisticsDAO, tickDAO);
        ticksDataStore.addStatisticsForTickPerInstrument(tickDAO, statisticsDAO);
    }

    private void appendTicksToInstrumentStatistics(StatisticsDAO statisticsDAO, TickDAO tickDAO) {
        statisticsDAO.setSum(statisticsDAO.getSum() + tickDAO.getPrice());
        statisticsDAO.setCount(statisticsDAO.getCount() + 1);
        statisticsDAO.setMax(Math.max(statisticsDAO.getMax(), tickDAO.getPrice()));
        statisticsDAO.setMin(Math.min(statisticsDAO.getMin(), tickDAO.getPrice()));

    }
}
