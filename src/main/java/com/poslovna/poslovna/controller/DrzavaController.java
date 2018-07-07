package com.poslovna.poslovna.controller;

import com.poslovna.poslovna.model.Drzava;
import com.poslovna.poslovna.service.DrzavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/drzava")
public class DrzavaController {

    @Autowired
    private DrzavaService drzavaService;

    @GetMapping
    public List<Drzava> getAllDrzava(){
        return drzavaService.getAllDrzava();
    }
}
