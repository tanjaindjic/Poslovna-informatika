package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.repository.KlijentRepository;
import com.poslovna.poslovna.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KlijentService {

    @Autowired
    private KlijentRepository klijentRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;


    public Klijent getKlijentPrekoKorisnikID(Long id) {
        Korisnik k = korisnikRepository.findById(id).get();
        return klijentRepository.findById(k.getKlijent().getId()).get();
    }

    public Klijent getKlijent(Long id){
        return klijentRepository.findById(id).get();
    }
}
