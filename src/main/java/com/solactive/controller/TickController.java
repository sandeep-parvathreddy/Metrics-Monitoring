package com.solactive.controller;

import com.solactive.controller.mapper.TickMapper;
import com.solactive.exception.TickProcessingException;
import com.solactive.model.TickDTO;
import com.solactive.service.TickService;
import com.solactive.util.TickStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by sandeepreddy on 22/03/20.
 */
@RestController
public class TickController {

   @Autowired
    TickService tickService;

    @PostMapping("/tick")
    public ResponseEntity postTick(@RequestBody TickDTO tickDTO)  {
        TickStatus tickStatus = null;
        try {
            tickStatus = tickService.addTick(TickMapper.makeTickDO(tickDTO));
        } catch (TickProcessingException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        if(tickStatus.equals(TickStatus.CREATED)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }


    }


}
