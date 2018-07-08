package com.poslovna.poslovna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.enums.TipKlijenta;

public interface KlijentRepository extends JpaRepository<Klijent, Long> {
	
	public List<Klijent> findByTip(TipKlijenta tip);
}
