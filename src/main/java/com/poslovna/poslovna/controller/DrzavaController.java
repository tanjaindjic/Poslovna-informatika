package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.service.DrzavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/drzava")
public class DrzavaController {

    @Autowired
    private DrzavaService drzavaService;
}
