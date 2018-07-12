package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaNalogodavcaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.exception.NevalidanIznosNovca;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.service.DnevnoStanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/dnevnoStanje")
public class DnevnoStanjeController {

    @Autowired
    private DnevnoStanjeService dnevnoStanjeService;

    @GetMapping(value="/kliring")
    private void obaviKliring() {
        dnevnoStanjeService.kliring();
    }
}
