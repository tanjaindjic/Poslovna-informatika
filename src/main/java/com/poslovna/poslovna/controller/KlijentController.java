package com.poslovna.poslovna.controller;


import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.service.KlijentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/klijent")
public class KlijentController {

    @Autowired
    private KlijentService klijentService;

    @GetMapping(value = "/{id}")
    public Klijent getKlijent(@PathVariable Long id){
        return klijentService.getKlijent(id);
    }
}
