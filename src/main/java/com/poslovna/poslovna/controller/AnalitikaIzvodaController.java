package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.dto.AnalitikaIzvodaDTO;
import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaNalogodavcaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.model.*;
import com.poslovna.poslovna.service.AnalitikaIzvodaService;
import com.poslovna.poslovna.service.KlijentService;
import com.poslovna.poslovna.service.KursUValutiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/analitikaIzvoda")
public class AnalitikaIzvodaController {

    @Autowired
    private AnalitikaIzvodaService analitikaIzvodaService;

    @Autowired
    private KlijentService klijentService;


    @GetMapping(value = "/{id}")
    public List<AnalitikaIzvoda> getIzvodiKlijenta(@PathVariable Long id){
        return analitikaIzvodaService.getIzvodiKlijenta(id);
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String createIzvod(@RequestBody AnalitikaIzvodaDTO dto) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException {
        
    	String retVal = "Vasa uplata je uspesno zabelezena.";
    	
    	try {
    		analitikaIzvodaService.createIzvod(dto);
    	}catch(Exception e) {
    		retVal = e.getMessage();
    	}
    	
    	return retVal;
    }
    @PostMapping(value = "/uplata", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUplata(@RequestBody AnalitikaIzvodaDTO dto) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException {
        return analitikaIzvodaService.createUplata(dto);
    }
    @PostMapping(value = "/isplata", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createIsplata(@RequestBody AnalitikaIzvodaDTO dto) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException {
        return analitikaIzvodaService.createIsplata(dto);
    }
}
