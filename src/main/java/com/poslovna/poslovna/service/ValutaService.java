package com.poslovna.poslovna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.Valuta;
import com.poslovna.poslovna.repository.ValutaRepository;

@Service
public class ValutaService {
	
	@Autowired
	private ValutaRepository valutaRepository;
	
	public List<Valuta> getAllValuta() {
		
		return valutaRepository.findAll();
	}
	
	public Valuta getOne(long id) {
		
		return valutaRepository.getOne(id);
	}
	
}
