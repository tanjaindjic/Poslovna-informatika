package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class AnalitikaIzvodaService {

    @Autowired
    private KlijentService klijentService;

    @Autowired
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;


    public List<AnalitikaIzvoda> getIzvodiKlijenta(Long id) {
        List<AnalitikaIzvoda> ret = new ArrayList<>();
        Klijent k = klijentService.getKlijentPrekoKorisnikID(id);
        for (Racun r: k.getRacuni()) {
            String brRacuna = r.getBrojRacuna();
            List<AnalitikaIzvoda> prim = getIzvodiPrimalac(brRacuna);
            List<AnalitikaIzvoda> pos = getIzvodiPosaljioc(brRacuna);
            ret.addAll(prim);
            ret.addAll(pos);
        }
        return filterResults(ret);

    }

    private List<AnalitikaIzvoda> filterResults(List<AnalitikaIzvoda> ret) {
        HashSet<AnalitikaIzvoda> hashSet = new HashSet(ret);
        return  new ArrayList<AnalitikaIzvoda>(hashSet);

    }

    private List<AnalitikaIzvoda> getIzvodiPosaljioc(String brRacuna) {
        return analitikaIzvodaRepository.findByRacunNalogodavca(brRacuna);
    }

    private List<AnalitikaIzvoda> getIzvodiPrimalac(String brRacuna) {
        return analitikaIzvodaRepository.findByRacunPrimaoca(brRacuna);
    }
}
