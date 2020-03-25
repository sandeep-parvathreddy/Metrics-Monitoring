package com.solactive.dataStore;

import com.solactive.dao.StatisticsDAO;
import com.solactive.dao.TickDAO;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by sandeepreddy on 22/03/20.
 */
@Repository
public class TicksDataStore {

    // store the statistics of ticks for every second
    private Map<Long,StatisticsDAO> ticksDataPerSecond;

    // store the statistics of ticks per instrument for every second
    private Map<String,Map<Long,StatisticsDAO>> ticksDataPerInstrumentPerSecond;


    @PostConstruct
    void init(){
        ticksDataPerSecond = new LinkedHashMap<>(60);
        ticksDataPerInstrumentPerSecond = new HashMap<>();
    }

    public void setTicksDataPerSecond(Map<Long, StatisticsDAO> ticksDataPerSecond) {
        this.ticksDataPerSecond = ticksDataPerSecond;
    }

    public void setTicksDataPerInstrumentPerSecond(Map<String, Map<Long, StatisticsDAO>> ticksDataPerInstrumentPerSecond) {
        this.ticksDataPerInstrumentPerSecond = ticksDataPerInstrumentPerSecond;
    }

    public void addStatisticsForTick(TickDAO tickDAO, StatisticsDAO statisticsDAO){
        ticksDataPerSecond.put(tickDAO.getTimestamp(), statisticsDAO);
    }

    public StatisticsDAO getStatisticsByTime(long timestamp){
        return ticksDataPerSecond.getOrDefault(timestamp,new StatisticsDAO());
    }

    public void addStatisticsForTickPerInstrument(TickDAO tickDAO, StatisticsDAO statisticsDAO){
        Map<Long,StatisticsDAO> dataPerInstrument = ticksDataPerInstrumentPerSecond.getOrDefault(tickDAO.getInstrument(),new LinkedHashMap<>(60));
        dataPerInstrument.put(tickDAO.getTimestamp(), statisticsDAO);
        ticksDataPerInstrumentPerSecond.put(tickDAO.getInstrument(),dataPerInstrument);
    }

    public StatisticsDAO getStatisticsPerInstrumentByTime(String instrument, long timestamp){
        if(instrument==null)
            return getStatisticsByTime(timestamp);
        else {
            Map<Long, StatisticsDAO> dataPerInstrument = ticksDataPerInstrumentPerSecond.get(instrument);
            return dataPerInstrument != null ? dataPerInstrument.getOrDefault(timestamp, new StatisticsDAO()) : new StatisticsDAO();
        }
    }



}
