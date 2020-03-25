package com.solactive.repository;

import com.solactive.dao.StatisticsDAO;
import com.solactive.exception.DataAccessException;

import java.util.List;

/**
 * Created by sandeepreddy on 22/03/20.
 */
public interface StatisticsRepository {

    StatisticsDAO get(long seconds) throws DataAccessException;

    StatisticsDAO get(long seconds, String instrument) throws DataAccessException;
}
