package com.poslovna.poslovna.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.Racun;

public interface DnevnoStanjeRepository extends JpaRepository<DnevnoStanje, Long> {
    DnevnoStanje findTopByOrderByDatumPrometaDesc();
    
    List<DnevnoStanje> findByDatumPrometaAndZaRacun(Date datumPrometa, Racun racun);
    
    DnevnoStanje findTopByZaRacunOrderByDatumPrometaDesc(Racun racun);
}
