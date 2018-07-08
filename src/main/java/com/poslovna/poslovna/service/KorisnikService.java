package com.poslovna.poslovna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.repository.KorisnikRepository;

@Service
public class KorisnikService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	public Korisnik getKorisnikByKorisnickoIme(String korisnickoIme) {
		
		return korisnikRepository.findByKorisnickoIme(korisnickoIme);
	}

}
