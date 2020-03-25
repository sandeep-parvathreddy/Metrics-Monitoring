package com.solactive.controller.mapper;

import com.solactive.dao.TickDAO;
import com.solactive.model.TickDTO;

/**
 * Created by sandeepreddy on 23/03/20.
 */
public class TickMapper {

    public static TickDAO makeTickDO(TickDTO tickDTO){
        return new TickDAO(tickDTO.getInstrument(), tickDTO.getPrice(), tickDTO.getTimestamp());
    }
}
