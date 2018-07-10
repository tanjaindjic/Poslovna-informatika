package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.repository.DnevnoStanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DnevnoStanjeService {

    @Autowired
    private DnevnoStanjeRepository dnevnoStanjeRepository;

    public DnevnoStanje getLatest(){
        return dnevnoStanjeRepository.findTopByOrderByDatumPrometaDesc();
    }

    public void kliring(){

    }

    public void updateDS(DnevnoStanje ds) {
        dnevnoStanjeRepository.save(ds);
    }
}
