package com.poslovna.poslovna.service;

import java.util.List;

import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.repository.KlijentRepository;
import com.poslovna.poslovna.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.model.enums.TipKlijenta;
import com.poslovna.poslovna.repository.KlijentRepository;
import com.poslovna.poslovna.repository.KorisnikRepository;

@Service
public class KlijentService {

    @Autowired
    private KlijentRepository klijentRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    
    public Klijent getKlijent(Long id) {
        Korisnik k = korisnikRepository.findById(id).get();
        return klijentRepository.findById(k.getKlijent().getId()).get();
    }

    public Klijent getKlijentPrekoKorisnikID(Long id) {
        Korisnik k = korisnikRepository.findById(id).get();
        return klijentRepository.findById(k.getKlijent().getId()).get();
    }
 
    public Klijent getOne(long id) {
    	
    	return klijentRepository.getOne(id);
    }
    
    public List<Klijent> getKlijentsByTip(TipKlijenta tip){
    	
    	return klijentRepository.findByTip(tip);
    }
    
    public Klijent saveKlinet(Klijent klijent) {
    	
    	return klijentRepository.save(klijent);
    }
    
}
