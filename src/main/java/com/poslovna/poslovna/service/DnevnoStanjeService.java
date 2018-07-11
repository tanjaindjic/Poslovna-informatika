package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.repository.DnevnoStanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DnevnoStanjeService {

    @Autowired
    private DnevnoStanjeRepository dnevnoStanjeRepository;

    @Autowired
    private AnalitikaIzvodaService analitikaIzvodaService;

    @Autowired
    private RacunService racunService;

    public DnevnoStanje getLatest(){
        return dnevnoStanjeRepository.findTopByOrderByDatumPrometaDesc();
    }

    public void kliring(){
        for(Racun r: racunService.getAllRacuni()){
            pojedinacniKliring(r);
        }
    }

    public void pojedinacniKliring(Racun r) {
        //analitike izvoda treba izmeniti status kad je obavljen prenos i kod primaoca i kod posiljaoca
        List<AnalitikaIzvoda> prim = analitikaIzvodaService.getEvidentiraniIzvodiPrimalac(r.getBrojRacuna());
        List<AnalitikaIzvoda> pos = analitikaIzvodaService.getEvidentiraniIzvodiPosaljioc(r.getBrojRacuna());

        DnevnoStanje poslednje = Collections.max(r.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        DnevnoStanje novo = new DnevnoStanje();
        novo.setDatumPrometa(new Date(System.currentTimeMillis()));
        novo.setPrethodnoStanje(poslednje.getNovoStanje());
        Float uKorist = 0F;
        for(AnalitikaIzvoda i : prim){
            uKorist=+i.getKonvertovaniIznos();
        }
        novo.setPrometUKorist(uKorist);
        Float naTeret = 0F;
        for(AnalitikaIzvoda i : pos){
            naTeret=+i.getKonvertovaniIznos();
        }
        novo.setPrometNaTeret(naTeret);
        novo.setNovoStanje(poslednje.getNovoStanje()+uKorist-naTeret);
        novo.setIzvodi(new ArrayList<>());
        novo.setZaRacun(r);
        dnevnoStanjeRepository.save(novo);
        r.getDnevnaStanja().add(novo);
        racunService.saveRacun(r);
    }

    public void updateDS(DnevnoStanje ds) {
        dnevnoStanjeRepository.save(ds);
    }
}
