package com.poslovna.poslovna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.poslovna.model.Racun;

public interface RacunRepository extends JpaRepository<Racun, Long> {
	
	public List<Racun> findByBrojRacuna(String brojRacuna);
	
	//public Page<Racun> findAll
	
}
