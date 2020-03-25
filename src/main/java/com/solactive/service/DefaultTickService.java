package com.solactive.service;

import com.solactive.repository.TickRepository;
import com.solactive.dao.TickDAO;
import com.solactive.exception.TickProcessingException;
import com.solactive.util.TickStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sandeepreddy on 24/03/20.
 */
@Service
public class DefaultTickService implements TickService{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultTickService.class);

    @Autowired
    TickRepository tickRepository;

    @Override
    public TickStatus addTick(TickDAO tickDAO) throws TickProcessingException {
        LOG.info("Processing the tick " + tickDAO);
        if(checkIfTickCanBeProcessed(tickDAO)){
            try {
                tickRepository.addTick(tickDAO);
                return TickStatus.CREATED;
            }
            catch (Exception e){
                LOG.error("Exception occurred in processing the tickRepository",e);
                throw new TickProcessingException(e.getMessage());
            }
        }
        else {
            LOG.info("Tick is older than 60 seconds, Hence ignoring it");
            return TickStatus.NOT_ACCEPTED;
        }

    }

    private boolean checkIfTickCanBeProcessed(TickDAO tickDAO) {
        return (System.currentTimeMillis()/1000)- tickDAO.getTimestamp() <=60;
    }
}
