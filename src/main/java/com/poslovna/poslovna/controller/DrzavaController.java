package com.poslovna.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.model.Drzava;
import com.poslovna.poslovna.service.DrzavaService;

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
