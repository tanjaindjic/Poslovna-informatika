package com.poslovna.poslovna.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.Ukidanje;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import com.poslovna.poslovna.repository.RacunRepository;
import com.poslovna.poslovna.repository.UkidanjeRepository;

@Service
public class RacunService {
	
	@Autowired
	private RacunRepository racunRepository;
	
	@Autowired 
	private AnalitikaIzvodaRepository analitikaIzvodaRepository;
	
	@Autowired
	private UkidanjeRepository ukidanjeRepository;
	
	public Racun saveRacun(Racun racun) {
		
		return racunRepository.save(racun);
	}
	
	public boolean checkIfUnique(String brojRacuna){
		
		return racunRepository.findByBrojRacuna(brojRacuna).isEmpty() ? true : false;
	}
	
	public List<Racun> getAllRacuni(){
		
		return racunRepository.findAll();
	}
	
	public Page<Racun> getRacuniByPage(Pageable pageable){
		
		return racunRepository.findAll(pageable);
	}
	
	public Racun aktiviraj(Racun racun) {
		
		if(racun == null) {
			return null;
		}
		
		Racun retVal = racunRepository.getOne(racun.getId());
		retVal.setVazeci(true);
		
		return racunRepository.save(retVal);
	}
	
	public Racun deaktiviraj(Racun racun, String naRacun) {
		
		if(racun == null) {
			return null;
		}
		
		Racun retVal = racunRepository.getOne(racun.getId());
		retVal.setVazeci(false);
		
		Ukidanje ukidanje = new Ukidanje(new Date(System.currentTimeMillis()), retVal, naRacun);
		ukidanjeRepository.save(ukidanje);
		
		return racunRepository.save(retVal);
	}
}
