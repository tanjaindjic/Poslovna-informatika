package com.poslovna.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.model.Banka;
import com.poslovna.poslovna.service.BankaService;

@RestController
@RequestMapping(value="/banka")
public class BankaController {

    @Autowired
    private BankaService bankaService;
    @RequestMapping(value = "/svi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Banka>> vratiBanke(){
    	
    	return new ResponseEntity<List<Banka>>(bankaService.vratiBanke(), HttpStatus.OK);
    }
    
    
}
