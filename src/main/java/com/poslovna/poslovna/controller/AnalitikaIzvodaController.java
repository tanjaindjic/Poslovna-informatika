package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.service.AnalitikaIzvodaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/analitikaIzvoda")
public class AnalitikaIzvodaController {

    @Autowired
    private AnalitikaIzvodaService analitikaIzvodaService;
}
