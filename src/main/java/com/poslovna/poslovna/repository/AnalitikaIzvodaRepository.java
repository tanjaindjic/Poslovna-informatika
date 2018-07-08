package com.poslovna.poslovna.repository;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalitikaIzvodaRepository extends JpaRepository<AnalitikaIzvoda, Long> {
    List<AnalitikaIzvoda> findByRacunNalogodavca(String brRacuna);

    List<AnalitikaIzvoda> findByRacunPrimaoca(String brRacuna);
}
