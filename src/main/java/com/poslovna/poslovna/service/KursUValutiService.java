package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.KursUValuti;
import com.poslovna.poslovna.model.Valuta;
import com.poslovna.poslovna.repository.KursUValutiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KursUValutiService {

    @Autowired
    private KursUValutiRepository kursUValutiRepository;

    public KursUValuti getKUVByValuta(Valuta od, Valuta ka) {
        return kursUValutiRepository.findByOsnovnaAndPrema(od, ka);
    }
}
