package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.service.AnalitikaIzvodaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/analitikaIzvoda")
public class AnalitikaIzvodaController {

    @Autowired
    private AnalitikaIzvodaService analitikaIzvodaService;

    @GetMapping(value = "/{id}")
    public List<AnalitikaIzvoda> getIzvodiKlijenta(@PathVariable Long id){
        return analitikaIzvodaService.getIzvodiKlijenta(id);
    }
}
