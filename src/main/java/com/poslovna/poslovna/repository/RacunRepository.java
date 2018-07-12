package com.poslovna.poslovna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.poslovna.model.Banka;
import com.poslovna.poslovna.model.Racun;

public interface RacunRepository extends JpaRepository<Racun, Long> {
	
	public Racun findByBrojRacuna(String brojRacuna);
	
	public Racun findByBrojRacunaAndPoslovnaBanka(String brojRacuna, Banka poslovnaBanka);
	
}
