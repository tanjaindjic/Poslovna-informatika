package com.poslovna.poslovna.service;

import com.poslovna.poslovna.dto.AnalitikaIzvodaDTO;
import com.poslovna.poslovna.model.*;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public String createIzvod(AnalitikaIzvodaDTO dto) {

        AnalitikaIzvoda a = new AnalitikaIzvoda();
        Klijent nalogodavac = klijentService.getOne(dto.getKlijentId());
        String racunNalogodavca = dto.getRacunNalogodavca();
        Racun saRacuna = null;
        boolean nasaoNalogodavca = false;
        for (Racun r :nalogodavac.getRacuni()) {
            if(r.getBrojRacuna().equals(racunNalogodavca)){
                saRacuna = r;
                nasaoNalogodavca = true;
                break;
            }
        }
        if(!nasaoNalogodavca){
            return "Nalogodavac nije pronadjen";
        }

        Racun naRacun = null;
        Klijent primalac = klijentService.findKlijentByRacun(dto.getRacunPrimaoca());
        if(primalac==null)
            return "Primalac nije pronadjen";

        for (Racun r :primalac.getRacuni()) {
            if(r.getBrojRacuna().equals(dto.getRacunPrimaoca())){
                naRacun = r;
                break;
            }
        }
        a.setNalogodavac(dto.getNalogodavac());
        a.setSvrhaPlacanja(dto.getSvrhaPlacanja());
        a.setPrimalac(dto.getPrimalac());
        a.setDatumPrijema(dto.getDatumPrijema());
        a.setDatumObrade(null);

        Valuta od = saRacuna.getValuta();
        Valuta ka = naRacun.getValuta();

        a.setValuta(od);
        a.setKrajnjaValuta(ka);
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
        a.setKonvertovaniIznos(krajnjiIznos);
        a.setDatumValute(zadnja.getDatum());
        a.setRacunNalogodavca(saRacuna.getBrojRacuna());
        a.setModelZaduzenja(dto.getModelZaduzenja());
        a.setPozivNaBroj(dto.getPozivNaBroj());
        a.setRacunPrimaoca(naRacun.getBrojRacuna());
        a.setModelOdobrenja(dto.getModelOdobrenja());
        a.setHitno(dto.isHitno());
        a.setTipGreske(0);
        a.setStatus(Status.E);
        a.setVrstaPlacanja(VrstaPlacanja.GOTOVINSKO);
        if(saRacuna.getBrojRacuna().substring(0, 3).equals(naRacun.getBrojRacuna().substring(0,3)))
            a.setMedjubankarski(false);
        else a.setMedjubankarski(true);
        a.setMestoPrijema(saRacuna.getPoslovnaBanka().getNaseljenoMesto());

        analitikaIzvodaRepository.save(a);

        return "Nalog je evidentiran.";
    }
}
