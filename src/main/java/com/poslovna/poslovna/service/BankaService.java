package com.poslovna.poslovna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.Banka;
import com.poslovna.poslovna.repository.BankaRepository;

@Service
public class BankaService {
	
	@Autowired
	private BankaRepository bankaReposiotry;
	
	public Banka getFirst() {
		
		return bankaReposiotry.findAll().get(0);
	}
	
}
