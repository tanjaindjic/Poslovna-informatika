package com.poslovna.poslovna.repository;

import com.poslovna.poslovna.model.Banka;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankaRepository extends JpaRepository<Banka, Long> {
	
	public List<Banka> findByBanka(boolean banka);
	
}
