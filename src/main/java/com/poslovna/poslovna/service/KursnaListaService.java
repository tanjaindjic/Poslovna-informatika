package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.KursnaLista;
import com.poslovna.poslovna.repository.KursUValutiRepository;
import com.poslovna.poslovna.repository.KursnaListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KursnaListaService {

    @Autowired
    private KursnaListaRepository kursnaListaRepository;

    public KursnaLista getLatest() {
        return kursnaListaRepository.findTopByOrderByVaziOdDesc();
    }
}
