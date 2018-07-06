package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.model.NaseljenoMesto;
import com.poslovna.poslovna.service.NaseljenoMestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/naseljenoMesto")
public class NaseljenoMestoController {

    @Autowired
    private NaseljenoMestoService naseljenoMestoService;
}
