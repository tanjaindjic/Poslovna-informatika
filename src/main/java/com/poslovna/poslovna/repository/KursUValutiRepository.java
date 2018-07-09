package com.poslovna.poslovna.repository;

import com.poslovna.poslovna.model.KursUValuti;
import com.poslovna.poslovna.model.Valuta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KursUValutiRepository extends JpaRepository<KursUValuti, Long> {
    KursUValuti findByOsnovnaAndPrema(Valuta od, Valuta ka);
}
