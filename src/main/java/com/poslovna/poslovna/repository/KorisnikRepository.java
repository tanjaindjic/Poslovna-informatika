package com.poslovna.poslovna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.poslovna.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{

	public Korisnik findByKorisnickoIme(String korisnickoIme);
	
}
