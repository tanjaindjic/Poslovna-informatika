package com.poslovna.poslovna.repository;

import com.poslovna.poslovna.model.Klijent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlijentRepository extends JpaRepository<Klijent, Long> {
}
