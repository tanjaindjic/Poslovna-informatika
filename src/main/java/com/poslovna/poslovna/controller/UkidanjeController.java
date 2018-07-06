package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.model.Ukidanje;
import com.poslovna.poslovna.service.UkidanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/ukidanje")
public class UkidanjeController {

    @Autowired
    private UkidanjeService ukidanjeService;
}
