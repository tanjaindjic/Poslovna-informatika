package com.poslovna.poslovna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.repository.RacunRepository;

@Service
public class RacunService {
	
	@Autowired
	private RacunRepository racunRepository;
	
	
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
}
