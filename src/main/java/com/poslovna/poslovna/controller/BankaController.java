package com.poslovna.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.service.BankaService;

@RestController
@RequestMapping(value="/banka")
public class BankaController {

    @Autowired
    private BankaService bankaService;
    
    
}
