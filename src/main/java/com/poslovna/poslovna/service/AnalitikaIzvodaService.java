package com.poslovna.poslovna.service;

import com.poslovna.poslovna.dto.AnalitikaIzvodaDTO;
import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaNalogodavcaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.model.*;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalitikaIzvodaService {

    @Autowired
    private KlijentService klijentService;


    @Autowired
    private KursnaListaService kursnaListaService;

    @Autowired
    private KursUValutiService kursUValutiService;

    @Autowired
    private RacunService racunService;

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
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public String createIzvod(AnalitikaIzvodaDTO dto) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException {

        AnalitikaIzvoda a = new AnalitikaIzvoda();
        Klijent nalogodavac = klijentService.getOne(dto.getKlijentId());
        Racun saRacuna = racunService.findRacunByBroj(dto.getRacunNalogodavca());

        if(nalogodavac==null){
            System.out.println("Nalogodavac nije nadjen u sistemu!");
            throw new NemaNalogodavcaException("Nalogodavac nije nadjen u sistemu!");
        }
        if(saRacuna==null){
            System.out.println("Racun nije nadjen u sistemu!");
            throw new NemaRacunaException("Racun nije nadjen u sistemu!");
        }
        DnevnoStanje trenutno = Collections.max(saRacuna.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        if(trenutno.getNovoStanje()-dto.getIznos()<0){
            System.out.println("Racun nije nadjen u sistemu!");
            throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
        }


        a.setNalogodavac(dto.getNalogodavac());
        a.setSvrhaPlacanja(dto.getSvrhaPlacanja());
        a.setPrimalac(dto.getPrimalac());
        a.setDatumPrijema(dto.getDatumPrijema());
        a.setDatumObrade(null);

        Racun naRacun = null;
        naRacun = racunService.findRacunByBroj(dto.getRacunPrimaoca());

        Valuta od = saRacuna.getValuta();
        Valuta ka;
        if(naRacun!=null)
            ka = naRacun.getValuta();
        else ka=saRacuna.getValuta();
        a.setValuta(od);
        a.setKrajnjaValuta(ka);
        //ovoliko se umanjuje nalogodavcu
        a.setIznos(dto.getIznos());

        KursUValuti kurs  = null;
        KursnaLista zadnja = kursnaListaService.getLatest();
        for(KursUValuti k : zadnja.getKursevi()){
            if(k.getOsnovna().getZvanicnaSifra().equals(od.getZvanicnaSifra()) && k.getPrema().getZvanicnaSifra().equals(ka.getZvanicnaSifra())){
                System.out.println("nasao kurs u valuti: " + k.getOsnovna() + " prema " + k.getPrema());
                kurs = k;
                break;
            }
        }
        Float krajnjiIznos = dto.getIznos()*kurs.getOdnos();
        //ovoliko se dodaje primaocu u zavisnosti od valute koja mu je za taj racun
        a.setKonvertovaniIznos(krajnjiIznos);
        a.setDatumValute(zadnja.getDatum());
        a.setRacunNalogodavca(saRacuna.getBrojRacuna());
        a.setModelZaduzenja(dto.getModelZaduzenja());
        a.setPozivNaBroj(dto.getPozivNaBroj());
        a.setRacunPrimaoca(naRacun.getBrojRacuna());
        a.setModelOdobrenja(dto.getModelOdobrenja());
        if(dto.getIznos()>250000F)
            a.setHitno(true);
        else a.setHitno(dto.isHitno());
        //ovo ne znam za gresku
        a.setTipGreske(0);
        a.setStatus(Status.E);
        //ni ovo ne znam kad sta
        a.setVrstaPlacanja(VrstaPlacanja.GOTOVINSKO);
        if(saRacuna.getPoslovnaBanka().getSifraBanke().equals(dto.getRacunPrimaoca().substring(0,3)))
            a.setMedjubankarski(false);
        else a.setMedjubankarski(true);
        a.setMestoPrijema(saRacuna.getPoslovnaBanka().getNaseljenoMesto());

        analitikaIzvodaRepository.save(a);
        //ovde pozivam da se prebace odmah sredstva ako je prenos unutar banke
        if(a.isMedjubankarski()){

        }
        return "Nalog je evidentiran.";
    }
}
