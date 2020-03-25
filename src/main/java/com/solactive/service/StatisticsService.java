package com.solactive.service;

import com.solactive.dao.StatisticsDAO;

/**
 * Created by sandeepreddy on 22/03/20.
 */
public interface StatisticsService {

    StatisticsDAO getStatistics();

    StatisticsDAO getStatistics(String instrumentName);

}
