package com.poslovna.poslovna.service;

import com.poslovna.poslovna.dto.AnalitikaIzvodaDTO;
import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaNalogodavcaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.exception.NevalidanIznosNovca;
import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.repository.DnevnoStanjeRepository;
import com.sun.org.apache.bcel.internal.generic.DNEG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DnevnoStanjeService {

    @Autowired
    private DnevnoStanjeRepository dnevnoStanjeRepository;

    @Autowired
    private AnalitikaIzvodaService analitikaIzvodaService;

    @Autowired
    private RacunService racunService;

    @Autowired
    private KlijentService klijentService;

    public DnevnoStanje getLatest(){
        return dnevnoStanjeRepository.findTopByOrderByDatumPrometaDesc();
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void kliring() throws NemaNalogodavcaException, NedovoljnoSredstavaException, NevalidanIznosNovca, NemaRacunaException {
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


    public void pojedinacniKliring(AnalitikaIzvoda a) throws NemaNalogodavcaException, NedovoljnoSredstavaException, NevalidanIznosNovca, NemaRacunaException {

        if(a.getIznos()<=0)
            throw new NevalidanIznosNovca("Iznos nije validan!");

        Racun primalac = racunService.findRacunByBroj(a.getRacunPrimaoca());
        if(primalac!=null && !primalac.isVazeci())
            throw new NemaRacunaException("Nije aktivan racun primaoca!");


        Racun r = racunService.findRacunByBroj(a.getRacunNalogodavca());
        if(r!=null){
            Date danas = new Date(System.currentTimeMillis());
            DnevnoStanje poslednje = Collections.max(r.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
            if(danas.getYear()==poslednje.getDatumPrometa().getYear() && danas.getMonth()==poslednje.getDatumPrometa().getMonth() && danas.getDay()==poslednje.getDatumPrometa().getDay())
                azurirajDnevnoStanje(a, r, poslednje);
            else kreirajDnevnoStanje(a, r, poslednje);

        }else{
            throw new NemaNalogodavcaException("Nalogodavac nije pronadjen.");
        }
        //proverim ako je primalac postoji azuriram kod njega, ako ne onda je to za narodnu banku

        if(primalac!=null) {
            Date danas = new Date(System.currentTimeMillis());
            DnevnoStanje poslednje = Collections.max(primalac.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
            if (danas.getYear() == poslednje.getDatumPrometa().getYear() && danas.getMonth() == poslednje.getDatumPrometa().getMonth() && danas.getDay() == poslednje.getDatumPrometa().getDay())
                azurirajDnevnoStanje(a, primalac, poslednje);
            else kreirajDnevnoStanje(a, primalac, poslednje);
        }


    }

    private void azurirajDnevnoStanje(AnalitikaIzvoda a, Racun r, DnevnoStanje poslednje) throws NedovoljnoSredstavaException {

        Float prethodno = poslednje.getNovoStanje();
        //u slucaju da obradjujem nalogodavca, njemu skidam pare
        if(a.getRacunNalogodavca().equals(r.getBrojRacuna())){
            provera(a, poslednje);
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


    private void kreirajDnevnoStanje(AnalitikaIzvoda a, Racun r, DnevnoStanje poslednje) throws NedovoljnoSredstavaException {
        //kreiranje novog dnevnog stanja za racun nalogodavca i eventualno za racun primaoca ako je iz nase banke
        DnevnoStanje novo = new DnevnoStanje();
        novo.setDatumPrometa(new Date(System.currentTimeMillis()));
        novo.setPrethodnoStanje(poslednje.getNovoStanje());
        //u slucaju da obradjujem nalogodavca, njemu skidam pare
        if(a.getRacunNalogodavca().equals(r.getBrojRacuna())){
            provera(a, poslednje);
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


    private void provera(AnalitikaIzvoda a, DnevnoStanje poslednje) throws NedovoljnoSredstavaException {
        if(poslednje.getNovoStanje()-a.getIznos()<0)
            throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");

        List<AnalitikaIzvoda> rezervisanaSredstva = analitikaIzvodaService.getEvidentiraniIzvodiPosaljioc(a.getRacunNalogodavca());
        Float rezervisanoIznos = 0F;
        for(AnalitikaIzvoda aiz: rezervisanaSredstva)
            //da ne ponovim trenutni nalog koji obradjujem
            if(aiz.getId()!=a.getId())
                rezervisanoIznos+=aiz.getIznos();
        System.out.println("Rezervisano: " + rezervisanoIznos);

        if(poslednje.getNovoStanje()-rezervisanoIznos-a.getIznos()<0){
            throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
        }
    }

    public void updateDS(DnevnoStanje ds) {
        dnevnoStanjeRepository.save(ds);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void kliringZaGasenje(Racun zaGasenje, String brojRacunaZaPrenos) throws NemaNalogodavcaException, NedovoljnoSredstavaException, NevalidanIznosNovca, NemaRacunaException {

        Racun primalac = racunService.findRacunByBroj(brojRacunaZaPrenos);
        if(primalac!=null && !primalac.isVazeci()){
            throw new NemaRacunaException("Racun primaoca nije vazeci.");
        }
        List<AnalitikaIzvoda> zaOdbijanje = analitikaIzvodaService.getEvidentiraniIzvodiPrimalac(zaGasenje.getBrojRacuna());
        zaOdbijanje.addAll(analitikaIzvodaService.getEvidentiraniIzvodiPosaljioc(zaGasenje.getBrojRacuna()));
        //ovde cu da obradim sve zahteve tako da prodje samo za racun za gasenje a da ostane u bazi neobradjeni kad se radi kliring za ostale racune
        //jer kad se bude radio kliring i trebal da se prebaci na nepostojeci racun samo ce se to preskociti a bice odradjeno za onaj drugi racun
        for(AnalitikaIzvoda a : zaOdbijanje){
            a.setStatus(Status.O);
            analitikaIzvodaService.update(a);
        }

        DnevnoStanje zadnje = Collections.max(zaGasenje.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        AnalitikaIzvodaDTO nalog = new AnalitikaIzvodaDTO();
        nalog.setDatumPlacanja(new java.sql.Date(System.currentTimeMillis()));
        nalog.setHitno(true);
        nalog.setIznos(zadnje.getNovoStanje());
        nalog.setKlijentId(klijentService.findKlijentByRacun(zaGasenje.getBrojRacuna()).getId());
        nalog.setModelOdobrenja(97);
        nalog.setModelZaduzenja(97);
        nalog.setRacunNalogodavca(zaGasenje.getBrojRacuna());
        nalog.setSvrhaPlacanja("Gasenje racuna - prenos sredstava");
        nalog.setRacunPrimaoca(brojRacunaZaPrenos);
        analitikaIzvodaService.createIzvod(nalog);
        Racun update = racunService.findRacunByBroj(zaGasenje.getBrojRacuna());
        update.setVazeci(false);
        racunService.saveRacun(update);


    }
    
    public DnevnoStanje getOne(long id) {
    	
    	return dnevnoStanjeRepository.getOne(id);
    }
}
