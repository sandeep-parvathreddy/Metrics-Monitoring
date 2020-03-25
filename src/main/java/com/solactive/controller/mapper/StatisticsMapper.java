package com.solactive.controller.mapper;

import com.solactive.dao.StatisticsDAO;
import com.solactive.model.StatisticsDTO;

/**
 * Created by sandeepreddy on 23/03/20.
 */
public class StatisticsMapper {

    public static StatisticsDTO makeStatistics(StatisticsDAO statisticsDAO){
        return new StatisticsDTO(statisticsDAO.getSum(), statisticsDAO.getMax(), statisticsDAO.getMin(), statisticsDAO.getCount());
    }

}
