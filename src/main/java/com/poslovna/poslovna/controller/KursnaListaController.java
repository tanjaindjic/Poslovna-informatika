package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.model.KursUValuti;
import com.poslovna.poslovna.model.KursnaLista;
import com.poslovna.poslovna.service.KursnaListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/kursnaLista")
public class KursnaListaController {

    @Autowired
    private KursnaListaService kursnaListaService;
}
