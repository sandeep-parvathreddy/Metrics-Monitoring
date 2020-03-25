package com.solactive.service;

import com.solactive.dao.TickDAO;
import com.solactive.exception.TickProcessingException;
import com.solactive.util.TickStatus;

/**
 * Created by sandeepreddy on 24/03/20.
 */
public interface TickService {

    TickStatus addTick(TickDAO tick) throws TickProcessingException;
}
