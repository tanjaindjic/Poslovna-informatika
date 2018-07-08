package com.poslovna.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.model.Valuta;
import com.poslovna.poslovna.service.ValutaService;

@RestController
@RequestMapping(value="/valuta")
public class ValutaController {

    @Autowired
    private ValutaService valutaService;
    
    
    @RequestMapping(value="/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Valuta>> preuzmiValute(){
    	
    	return new ResponseEntity<List<Valuta>>(valutaService.getAllValuta(), HttpStatus.OK);
    }
}
