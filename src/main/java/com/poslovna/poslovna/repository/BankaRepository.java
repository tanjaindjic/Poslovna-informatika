package com.poslovna.poslovna.repository;

import com.poslovna.poslovna.model.Banka;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankaRepository extends JpaRepository<Banka, Long> {
}
