package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.model.KursUValuti;
import com.poslovna.poslovna.service.KursUValutiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/kursUValuti")
public class KursUValutiController {

    @Autowired
    private KursUValutiService kursUValutiService;
}
