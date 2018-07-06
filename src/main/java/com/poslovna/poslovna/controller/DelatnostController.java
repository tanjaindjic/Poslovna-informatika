package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.service.DelatnostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/delatnost")
public class DelatnostController {

    @Autowired
    private DelatnostService delatnostService;
}
