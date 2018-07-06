package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.service.ValutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/valuta")
public class ValutaController {

    @Autowired
    private ValutaService valutaService;
}
