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
    private DnevnoStanjeService dnevnoStanjeService;

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

    public List<AnalitikaIzvoda> getEvidentiraniIzvodiPosaljioc(String brRacuna) {
        return analitikaIzvodaRepository.findByRacunNalogodavcaAndStatus(brRacuna, Status.E);
    }


    private List<AnalitikaIzvoda> getIzvodiPrimalac(String brRacuna) {
        return analitikaIzvodaRepository.findByRacunPrimaoca(brRacuna);
    }

    public List<AnalitikaIzvoda> getEvidentiraniIzvodiPrimalac(String brRacuna) {
        return analitikaIzvodaRepository.findByRacunPrimaocaAndStatus(brRacuna, Status.E);
    }
    
    public AnalitikaIzvoda getOne(long id){
    	
    	return analitikaIzvodaRepository.getOne(id);
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
            System.out.println("Nedovoljno sredstava!");
            throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
        }
        List<AnalitikaIzvoda> rezervisanaSredstva = analitikaIzvodaRepository.findByRacunNalogodavcaAndStatus(dto.getRacunNalogodavca(), Status.E);
        Float rezervisanoIznos = 0F;
        for(AnalitikaIzvoda aiz: rezervisanaSredstva)
            rezervisanoIznos+=aiz.getIznos();
        System.out.println("Rezervisano: " + rezervisanoIznos);
        if(trenutno.getNovoStanje()-rezervisanoIznos<0){
            System.out.println("Nedovoljno sredstava!");
            throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
        }

        a.setNalogodavac(dto.getNalogodavac());
        a.setSvrhaPlacanja(dto.getSvrhaPlacanja());
        a.setPrimalac(dto.getPrimalac());
        a.setDatumPrijema(new Date(System.currentTimeMillis()));
        a.setDatumPlacanja(dto.getDatumPlacanja());
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
        a.setRacunPrimaoca(dto.getRacunPrimaoca());
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
        if(!a.isMedjubankarski()){
            Date odbraniDatum = dto.getDatumPlacanja();
            Date danas = new Date(System.currentTimeMillis());
            //SAMO AKO DANAS ZELI DA SE IZVRSI INTERNO PREBACIVANJE ONDA, INACE CEKA KLIRING ZA ODABRANI DAN
            if(danas.getYear()==odbraniDatum.getYear() && danas.getMonth()==odbraniDatum.getMonth() && danas.getDay()==odbraniDatum.getDay() )
                prebaciSredstva(saRacuna, naRacun, a);
        }
        return "Nalog je evidentiran.";
    }

    public void prebaciSredstva(Racun saRacuna, Racun naRacun, AnalitikaIzvoda nalogZaPrenos){
        //prvo azuriram ili kreiram novo dnevno stanje raucna sa kojeg skidam
        DnevnoStanje ds = Collections.max(saRacuna.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        Date datumDS = ds.getDatumPrometa();
        Date danas = new Date(System.currentTimeMillis());
        if(datumDS.getYear()==danas.getYear() && datumDS.getMonth()==danas.getMonth() && datumDS.getDay()==danas.getDay()){
            //ima danasnje stanje samo azuriram
            Float trenutnoNaTeret = ds.getPrometNaTeret();
            ds.setPrometNaTeret(trenutnoNaTeret + nalogZaPrenos.getIznos());
            ds.setPrethodnoStanje(ds.getNovoStanje());
            ds.setNovoStanje(ds.getPrethodnoStanje()-nalogZaPrenos.getIznos());
            ds.getIzvodi().add(nalogZaPrenos);
            dnevnoStanjeService.updateDS(ds);
            racunService.saveRacun(saRacuna);
        }else{
            DnevnoStanje novo = new DnevnoStanje();
            novo.setPrethodnoStanje(ds.getNovoStanje());
            Float trenutnoNaTeret = ds.getPrometNaTeret();
            novo.setPrometNaTeret(trenutnoNaTeret + nalogZaPrenos.getIznos());
            novo.setPrometUKorist(ds.getPrometUKorist());
            novo.setNovoStanje(ds.getNovoStanje()-nalogZaPrenos.getIznos());
            novo.setIzvodi(new ArrayList<>());
            novo.setZaRacun(ds.getZaRacun());
            novo.setDatumPrometa(danas);
            novo.getIzvodi().add(nalogZaPrenos);
            dnevnoStanjeService.updateDS(novo);
            saRacuna.getDnevnaStanja().add(novo);
            racunService.saveRacun(saRacuna);
        }
        //sad za primaoca
        DnevnoStanje ds2 = Collections.max(naRacun.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        Date datumDS2 = ds2.getDatumPrometa();

        if(datumDS2.getYear()==danas.getYear() && datumDS2.getMonth()==danas.getMonth() && datumDS2.getDay()==danas.getDay()){
            //ima danasnje stanje samo azuriram
            Float trenutnoUKorist = ds2.getPrometUKorist();
            ds2.setPrometUKorist(trenutnoUKorist + nalogZaPrenos.getKonvertovaniIznos());
            ds2.setPrethodnoStanje(ds2.getNovoStanje());
            ds2.setNovoStanje(ds2.getPrethodnoStanje()+nalogZaPrenos.getKonvertovaniIznos());
            ds2.getIzvodi().add(nalogZaPrenos);
            dnevnoStanjeService.updateDS(ds2);
            racunService.saveRacun(naRacun);
        }else{
            DnevnoStanje novo = new DnevnoStanje();
            novo.setPrethodnoStanje(ds2.getNovoStanje());
            Float trenutnoUKorist = ds2.getPrometUKorist();
            novo.setPrometNaTeret(ds2.getPrometNaTeret());
            novo.setPrometUKorist(trenutnoUKorist + nalogZaPrenos.getKonvertovaniIznos());
            novo.setNovoStanje(ds2.getNovoStanje()+nalogZaPrenos.getKonvertovaniIznos());
            novo.setIzvodi(new ArrayList<>());
            novo.setZaRacun(ds2.getZaRacun());
            novo.setDatumPrometa(danas);
            novo.getIzvodi().add(nalogZaPrenos);
            dnevnoStanjeService.updateDS(novo);
            naRacun.getDnevnaStanja().add(novo);
            racunService.saveRacun(naRacun);
        }
        nalogZaPrenos.setStatus(Status.I);
        nalogZaPrenos.setDatumObrade(danas);
        analitikaIzvodaRepository.save(nalogZaPrenos);


    }
}
