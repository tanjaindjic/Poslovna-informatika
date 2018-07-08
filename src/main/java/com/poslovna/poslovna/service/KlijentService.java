package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.repository.KlijentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KlijentService {

    @Autowired
    private KlijentRepository klijentRepository;

    public Klijent getKlijent(Long id) {
        return klijentRepository.getOne(id);
    }
}
