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

    @PostMapping
    public String createIzvod(@RequestBody AnalitikaIzvodaDTO dto) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException {
        return analitikaIzvodaService.createIzvod(dto);
    }
}
