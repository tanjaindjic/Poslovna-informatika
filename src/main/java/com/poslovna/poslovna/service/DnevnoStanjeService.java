package com.poslovna.poslovna.service;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.repository.DnevnoStanjeRepository;
import com.sun.org.apache.bcel.internal.generic.DNEG;
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
        for(AnalitikaIzvoda a: analitikaIzvodaService.getAllEvidentirani()){
            Date danas = new Date(System.currentTimeMillis());
            //ako je klijent stavio da se pare prenesu negde u buducnosti onda to sad ne diramo, stoje kao rezervisana sredstva
            if(a.getDatumPlacanja().getYear()<=danas.getYear() && a.getDatumPlacanja().getMonth()<=danas.getMonth() && a.getDatumPlacanja().getDay()<=danas.getDay()){
                pojedinacniKliring(a);
                a.setDatumObrade(danas);
                a.setStatus(Status.I);
                analitikaIzvodaService.update(a);
            }

        }
    }


    public void pojedinacniKliring(AnalitikaIzvoda a) {
        Racun r = racunService.findRacunByBroj(a.getRacunNalogodavca());
        if(r!=null){
            Date danas = new Date(System.currentTimeMillis());
            DnevnoStanje poslednje = Collections.max(r.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
            if(danas.getYear()==poslednje.getDatumPrometa().getYear() && danas.getMonth()==poslednje.getDatumPrometa().getMonth() && danas.getDay()==poslednje.getDatumPrometa().getDay())
                azurirajDnevnoStanje(a, r, poslednje);
            else kreirajDnevnoStanje(a, r, poslednje);

        }
        //proverim ako je primalac postoji azuriram kod njega, ako ne onda je to za narodnu banku
        Racun primalac = racunService.findRacunByBroj(a.getRacunPrimaoca());
        if(primalac!=null) {
            Date danas = new Date(System.currentTimeMillis());
            DnevnoStanje poslednje = Collections.max(primalac.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
            if (danas.getYear() == poslednje.getDatumPrometa().getYear() && danas.getMonth() == poslednje.getDatumPrometa().getMonth() && danas.getDay() == poslednje.getDatumPrometa().getDay())
                azurirajDnevnoStanje(a, primalac, poslednje);
            else kreirajDnevnoStanje(a, primalac, poslednje);
        }


    }

    private void azurirajDnevnoStanje(AnalitikaIzvoda a, Racun r, DnevnoStanje poslednje) {

        Float prethodno = poslednje.getNovoStanje();
        //u slucaju da obradjujem nalogodavca, njemu skidam pare
        if(a.getRacunNalogodavca().equals(r.getBrojRacuna())){
            Float trenutno = poslednje.getPrometNaTeret();
            poslednje.setPrometNaTeret(trenutno + a.getIznos());
            poslednje.setNovoStanje(prethodno-a.getIznos());
        }else{ //u ovom slucaju je primalac i njemu dodajem pare
            Float trenutno = poslednje.getPrometUKorist();
            poslednje.setPrometUKorist(trenutno + a.getKonvertovaniIznos());
            poslednje.setNovoStanje(prethodno+a.getKonvertovaniIznos());
        }
        poslednje.getIzvodi().add(a);
        dnevnoStanjeRepository.save(poslednje);
    }

    private void kreirajDnevnoStanje(AnalitikaIzvoda a, Racun r, DnevnoStanje poslednje) {
        //kreiranje novog dnevnog stanja za racun nalogodavca i eventualno za racun primaoca ako je iz nase banke
        DnevnoStanje novo = new DnevnoStanje();
        novo.setDatumPrometa(new Date(System.currentTimeMillis()));
        novo.setPrethodnoStanje(poslednje.getNovoStanje());
        //u slucaju da obradjujem nalogodavca, njemu skidam pare
        if(a.getRacunNalogodavca().equals(r.getBrojRacuna())){
            novo.setPrometUKorist(0F);
            novo.setPrometNaTeret(a.getIznos());
            novo.setNovoStanje(poslednje.getNovoStanje()-a.getIznos());
        }else{ //u ovom slucaju je primalac i njemu dodajem pare
            novo.setPrometUKorist(a.getKonvertovaniIznos());
            novo.setPrometNaTeret(0F);
            novo.setNovoStanje(poslednje.getNovoStanje()+a.getKonvertovaniIznos());
        }

        novo.setIzvodi(new ArrayList<>());
        novo.getIzvodi().add(a);
        novo.setZaRacun(r);
        dnevnoStanjeRepository.save(novo);
        r.getDnevnaStanja().add(novo);
        racunService.saveRacun(r);

    }

    public void updateDS(DnevnoStanje ds) {
        dnevnoStanjeRepository.save(ds);
    }

    public void kliringZaGasenje(Racun r) {
        List<AnalitikaIzvoda> zaOdbijanje = analitikaIzvodaService.getEvidentiraniIzvodiPrimalac(r.getBrojRacuna());
        zaOdbijanje.addAll(analitikaIzvodaService.getEvidentiraniIzvodiPosaljioc(r.getBrojRacuna()));
        //ovde cu da obradim sve zahteve tako da prodje samo za racun za gasenje a da ostane u bazi neobradjeni kad se radi kliring za ostale racune
        //jer kad se bude radio kliring i trebal da se prebaci na nepostojeci racun samo ce se to preskociti a bice odradjeno za onaj drugi racun
        for(AnalitikaIzvoda a : zaOdbijanje){
            a.setStatus(Status.O);
            analitikaIzvodaService.update(a);
        }

    }
    
    public DnevnoStanje getOne(long id) {
    	
    	return dnevnoStanjeRepository.getOne(id);
    }
}
