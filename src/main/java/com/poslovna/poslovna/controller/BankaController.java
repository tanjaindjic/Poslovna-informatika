package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.service.BankaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/banka")
public class BankaController {

    @Autowired
    private BankaService bankaService;
}
