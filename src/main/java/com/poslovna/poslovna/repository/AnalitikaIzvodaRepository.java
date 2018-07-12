package com.poslovna.poslovna.repository;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalitikaIzvodaRepository extends JpaRepository<AnalitikaIzvoda, Long> {
    List<AnalitikaIzvoda> findByRacunNalogodavca(String brRacuna);
    List<AnalitikaIzvoda> findByRacunNalogodavcaAndStatus(String brRacuna, Status status);
    List<AnalitikaIzvoda> findByRacunPrimaoca(String brRacuna);
    List<AnalitikaIzvoda> findByRacunPrimaocaAndStatus(String brRacuna, Status status);
    List<AnalitikaIzvoda> findByStatus(Status e);
    List<AnalitikaIzvoda> findByRacunNalogodavcaOrRacunPrimaoca(String nalogodavac, String primalac);
}
