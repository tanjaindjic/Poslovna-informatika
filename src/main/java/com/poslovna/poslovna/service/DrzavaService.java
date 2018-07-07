package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.Drzava;
import com.poslovna.poslovna.repository.DrzavaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrzavaService {

    @Autowired
    private DrzavaRepository drzavaRepository;

    public List<Drzava> getAllDrzava() {
        return drzavaRepository.findAll();
    }
}
