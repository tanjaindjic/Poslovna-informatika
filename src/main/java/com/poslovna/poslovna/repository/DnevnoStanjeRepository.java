package com.poslovna.poslovna.repository;

import com.poslovna.poslovna.model.DnevnoStanje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DnevnoStanjeRepository extends JpaRepository<DnevnoStanje, Long> {
    DnevnoStanje findTopByOrderByDatumPrometaDesc();
}
