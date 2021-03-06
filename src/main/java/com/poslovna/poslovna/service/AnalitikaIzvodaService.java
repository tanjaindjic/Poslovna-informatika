package com.poslovna.poslovna.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.poslovna.poslovna.dto.AnalitikaIzvodaDTO;
import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaNalogodavcaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.exception.NevalidanIznosNovca;
import com.poslovna.poslovna.model.*;
import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.KursUValuti;
import com.poslovna.poslovna.model.KursnaLista;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.Valuta;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;

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
    public String createIzvod(AnalitikaIzvodaDTO dto) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException, NevalidanIznosNovca {

        AnalitikaIzvoda a = new AnalitikaIzvoda();
        Klijent nalogodavac = klijentService.getOne(dto.getKlijentId());
        Racun saRacuna = racunService.findRacunByBroj(dto.getRacunNalogodavca());
        Racun naRacun = racunService.findRacunByBroj(dto.getRacunPrimaoca());

        if(nalogodavac==null){
            throw new NemaNalogodavcaException("Nalogodavac nije nadjen u sistemu!");
        }
        
        if(saRacuna==null){
            throw new NemaRacunaException("Racun nije nadjen u sistemu!");
        }
        
        if(naRacun!=null && !naRacun.isVazeci()){
            throw new NemaRacunaException("Racun nije aktivan!");
        }
        
        if(!saRacuna.isVazeci()) {
            throw new NemaRacunaException("Racun nije aktivan!");
        }

        if(dto.getIznos()<=0)
            throw new NevalidanIznosNovca("Iznos nije validan!");

        DnevnoStanje trenutno = Collections.max(saRacuna.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        if(trenutno.getNovoStanje()-dto.getIznos()<0){
            System.out.println("Nedovoljno sredstava! linija 113");
            throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
        }
        
        List<AnalitikaIzvoda> rezervisanaSredstva = analitikaIzvodaRepository.findByRacunNalogodavcaAndStatus(dto.getRacunNalogodavca(), Status.E);
        Float rezervisanoIznos = 0F;
        for(AnalitikaIzvoda aiz: rezervisanaSredstva)
            rezervisanoIznos+=aiz.getIznos();
        System.out.println("Rezervisano: " + rezervisanoIznos);
        
        if(trenutno.getNovoStanje()-rezervisanoIznos-dto.getIznos()<0){
            throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
        }



        a.setNalogodavac(dto.getNalogodavac());
        a.setSvrhaPlacanja(dto.getSvrhaPlacanja());
        a.setPrimalac(dto.getPrimalac());
        a.setDatumPrijema(new Date(System.currentTimeMillis()));
        a.setDatumPlacanja(dto.getDatumPlacanja());
        a.setDatumObrade(null);

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
            if(naRacun!=null)
                a.setMedjubankarski(false);
        else a.setMedjubankarski(true);
        a.setMestoPrijema(saRacuna.getPoslovnaBanka().getNaseljenoMesto());

        analitikaIzvodaRepository.save(a);
        //ovde pozivam da se prebace odmah sredstva ako je prenos unutar banke
        if(!a.isMedjubankarski() || a.isHitno()){
            Date odbraniDatum = dto.getDatumPlacanja();
            Date danas = new Date(System.currentTimeMillis());
            //SAMO AKO DANAS ZELI DA SE IZVRSI INTERNO PREBACIVANJE ONDA, INACE CEKA KLIRING ZA ODABRANI DAN
            if((danas.getYear()==odbraniDatum.getYear() && danas.getMonth()==odbraniDatum.getMonth() && danas.getDay()==odbraniDatum.getDay()) || a.isHitno())
                prebaciSredstva(saRacuna, naRacun, a);
        }
        return "Nalog uspesno kreiran.";
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
            //ds.setPrethodnoStanje(ds.getNovoStanje());
            Float prethodno = ds.getNovoStanje();
            ds.setNovoStanje(prethodno-nalogZaPrenos.getIznos());
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
            System.out.println(saRacuna.getDnevnaStanja().size());
        }
        //sad za primaoca
        DnevnoStanje ds2 = Collections.max(naRacun.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        Date datumDS2 = ds2.getDatumPrometa();

        if(datumDS2.getYear()==danas.getYear() && datumDS2.getMonth()==danas.getMonth() && datumDS2.getDay()==danas.getDay()){
            //ima danasnje stanje samo azuriram
            Float trenutnoUKorist = ds2.getPrometUKorist();
            ds2.setPrometUKorist(trenutnoUKorist + nalogZaPrenos.getKonvertovaniIznos());
            Float prethodno = ds2.getNovoStanje();
            //ds2.setPrethodnoStanje(ds2.getNovoStanje());
            ds2.setNovoStanje(prethodno+nalogZaPrenos.getKonvertovaniIznos());
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

    public List<AnalitikaIzvoda> getAllEvidentirani() {
        return analitikaIzvodaRepository.findByStatus(Status.E);
    }
    public List<AnalitikaIzvoda> getAllOdbijeni() {
        return analitikaIzvodaRepository.findByStatus(Status.O);
    }

    public void update(AnalitikaIzvoda a) {
        analitikaIzvodaRepository.save(a);
    }

	public String createUplata(AnalitikaIzvodaDTO dto) {
		AnalitikaIzvoda a = new AnalitikaIzvoda();
   //     Klijent nalogodavac = klijentService.getOne(dto.getKlijentId());
   //     Racun saRacuna = racunService.findRacunByBroj(dto.getRacunNalogodavca());
        Racun naRacun = racunService.findRacunByBroj(dto.getRacunPrimaoca());

  /*      if(nalogodavac==null){
            System.out.println("Nalogodavac nije nadjen u sistemu! linija 96");
            //throw new NemaNalogodavcaException("Nalogodavac nije nadjen u sistemu!");
            return "Nalogodavac nije nadjen u sistemu.";
        }
        if(saRacuna==null){
            System.out.println("Racun nije nadjen u sistemu linija 100!");
            //throw new NemaRacunaException("Racun nije nadjen u sistemu!");
            return "Racun nije nadjen u sistemu.";

        }*/
        if(naRacun!=null && !naRacun.isVazeci()){
            System.out.println("Racun nije aktivan! linija 104");
            //throw new NemaRacunaException("Racun nije aktivan!");
            return "Racun primaoca nije aktivan.";
        }

        a.setNalogodavac(dto.getNalogodavac());
        a.setSvrhaPlacanja(dto.getSvrhaPlacanja());
        a.setPrimalac(dto.getPrimalac());
        a.setDatumPrijema(new Date(System.currentTimeMillis()));
        a.setDatumPlacanja(dto.getDatumPlacanja());
        a.setDatumObrade(null);

        
        a.setIznos(dto.getIznos());

        KursUValuti kurs  = null;
        KursnaLista zadnja = kursnaListaService.getLatest();
  /*      for(KursUValuti k : zadnja.getKursevi()){
            if(k.getOsnovna().getZvanicnaSifra().equals(od.getZvanicnaSifra()) && k.getPrema().getZvanicnaSifra().equals(ka.getZvanicnaSifra())){
                System.out.println("nasao kurs u valuti: " + k.getOsnovna() + " prema " + k.getPrema());
                kurs = k;
                break;
            }
        }*/
   //     Float krajnjiIznos = dto.getIznos()*kurs.getOdnos();
        //ovoliko se dodaje primaocu u zavisnosti od valute koja mu je za taj racun
        a.setKonvertovaniIznos(dto.getIznos());
        a.setDatumValute(zadnja.getDatum());
   //     a.setRacunNalogodavca(saRacuna.getBrojRacuna());
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
        a.setMedjubankarski(false);
        a.setMestoPrijema(naRacun.getPoslovnaBanka().getNaseljenoMesto());

        analitikaIzvodaRepository.save(a);
        //ovde pozivam da se prebace odmah sredstva ako je prenos unutar banke
        if(!a.isMedjubankarski() || a.isHitno()){
            Date odbraniDatum = dto.getDatumPlacanja();
            Date danas = new Date(System.currentTimeMillis());
            //SAMO AKO DANAS ZELI DA SE IZVRSI INTERNO PREBACIVANJE ONDA, INACE CEKA KLIRING ZA ODABRANI DAN
            if((danas.getYear()==odbraniDatum.getYear() && danas.getMonth()==odbraniDatum.getMonth() && danas.getDay()==odbraniDatum.getDay()) || a.isHitno())
                prebaciSredstva(naRacun, a);
        }
        return "Nalog uspesno izvrsen.";
	}

	private void prebaciSredstva(Racun naRacun, AnalitikaIzvoda a) {
	        //sad za primaoca
	        DnevnoStanje ds2 = Collections.max(naRacun.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
	        Date datumDS2 = ds2.getDatumPrometa();
	        Date danas = new Date(System.currentTimeMillis());
	        if(datumDS2.getYear()==danas.getYear() && datumDS2.getMonth()==danas.getMonth() && datumDS2.getDay()==danas.getDay()){
	            //ima danasnje stanje samo azuriram
	            Float trenutnoUKorist = ds2.getPrometUKorist();
	            ds2.setPrometUKorist(trenutnoUKorist + a.getKonvertovaniIznos());
	            Float prethodno = ds2.getNovoStanje();
	            //ds2.setPrethodnoStanje(ds2.getNovoStanje());
	            ds2.setNovoStanje(prethodno+a.getKonvertovaniIznos());
	            ds2.getIzvodi().add(a);
	            dnevnoStanjeService.updateDS(ds2);
	            racunService.saveRacun(naRacun);
	        }else{
	            DnevnoStanje novo = new DnevnoStanje();
	            novo.setPrethodnoStanje(ds2.getNovoStanje());
	            Float trenutnoUKorist = ds2.getPrometUKorist();
	            novo.setPrometNaTeret(ds2.getPrometNaTeret());
	            novo.setPrometUKorist(trenutnoUKorist + a.getKonvertovaniIznos());
	            novo.setNovoStanje(ds2.getNovoStanje()+a.getKonvertovaniIznos());
	            novo.setIzvodi(new ArrayList<>());
	            novo.setZaRacun(ds2.getZaRacun());
	            novo.setDatumPrometa(danas);
	            novo.getIzvodi().add(a);
	            dnevnoStanjeService.updateDS(novo);
	            naRacun.getDnevnaStanja().add(novo);
	            racunService.saveRacun(naRacun);
	        }
	        a.setStatus(Status.I);
	        a.setDatumObrade(danas);
	        analitikaIzvodaRepository.save(a);


	}

	public String createIsplata(AnalitikaIzvodaDTO dto) {
		AnalitikaIzvoda a = new AnalitikaIzvoda();
        Klijent nalogodavac = klijentService.getOne(dto.getKlijentId());
        Racun saRacuna = racunService.findRacunByBroj(dto.getRacunNalogodavca());

        if(saRacuna!=null || !saRacuna.isVazeci()){
           // throw new NemaRacunaException("Racun nije aktivan!");
        }


        DnevnoStanje trenutno = Collections.max(saRacuna.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        if(trenutno.getNovoStanje()-dto.getIznos()<0){
            System.out.println("Nedovoljno sredstava! linija 113");
       //     throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
            return "Nema dovoljno sredstava za isplatu";
        }

        List<AnalitikaIzvoda> rezervisanaSredstva = analitikaIzvodaRepository.findByRacunNalogodavcaAndStatus(dto.getRacunNalogodavca(), Status.E);
        Float rezervisanoIznos = 0F;
        for(AnalitikaIzvoda aiz: rezervisanaSredstva)
            rezervisanoIznos+=aiz.getIznos();
        System.out.println("Rezervisano: " + rezervisanoIznos);

        if(trenutno.getNovoStanje()-rezervisanoIznos-dto.getIznos()<0){
        //    throw new NedovoljnoSredstavaException("Nedovoljno sredstava!");
        }

        a.setNalogodavac(dto.getNalogodavac());
        a.setSvrhaPlacanja(dto.getSvrhaPlacanja());
        a.setPrimalac(dto.getPrimalac());
        a.setDatumPrijema(new Date(System.currentTimeMillis()));
        a.setDatumPlacanja(dto.getDatumPlacanja());
        a.setDatumObrade(null);

        Valuta od = saRacuna.getValuta();
        Valuta ka;
        ka=saRacuna.getValuta();
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
        a.setMedjubankarski(false);
        a.setMestoPrijema(saRacuna.getPoslovnaBanka().getNaseljenoMesto());

        analitikaIzvodaRepository.save(a);
        //ovde pozivam da se prebace odmah sredstva ako je prenos unutar banke
        if(!a.isMedjubankarski() || a.isHitno()){
            Date odbraniDatum = dto.getDatumPlacanja();
            Date danas = new Date(System.currentTimeMillis());
            //SAMO AKO DANAS ZELI DA SE IZVRSI INTERNO PREBACIVANJE ONDA, INACE CEKA KLIRING ZA ODABRANI DAN
            if((danas.getYear()==odbraniDatum.getYear() && danas.getMonth()==odbraniDatum.getMonth() && danas.getDay()==odbraniDatum.getDay()) || a.isHitno())
                skiniSredstva(saRacuna, a);
        }
        return "Ispata uspesno izvrsena.";
	}

	private void skiniSredstva(Racun saRacuna, AnalitikaIzvoda a) {
		//prvo azuriram ili kreiram novo dnevno stanje raucna sa kojeg skidam
        DnevnoStanje ds = Collections.max(saRacuna.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
        Date datumDS = ds.getDatumPrometa();
        Date danas = new Date(System.currentTimeMillis());
        if(datumDS.getYear()==danas.getYear() && datumDS.getMonth()==danas.getMonth() && datumDS.getDay()==danas.getDay()){
            //ima danasnje stanje samo azuriram
            Float trenutnoNaTeret = ds.getPrometNaTeret();
            ds.setPrometNaTeret(trenutnoNaTeret + a.getIznos());
            //ds.setPrethodnoStanje(ds.getNovoStanje());
            Float prethodno = ds.getNovoStanje();
            ds.setNovoStanje(prethodno-a.getIznos());
            ds.getIzvodi().add(a);
            dnevnoStanjeService.updateDS(ds);
            racunService.saveRacun(saRacuna);
        }else{
            DnevnoStanje novo = new DnevnoStanje();
            novo.setPrethodnoStanje(ds.getNovoStanje());
            Float trenutnoNaTeret = ds.getPrometNaTeret();
            novo.setPrometNaTeret(trenutnoNaTeret + a.getIznos());
            novo.setPrometUKorist(ds.getPrometUKorist());
            novo.setNovoStanje(ds.getNovoStanje()-a.getIznos());
            novo.setIzvodi(new ArrayList<>());
            novo.setZaRacun(ds.getZaRacun());
            novo.setDatumPrometa(danas);
            novo.getIzvodi().add(a);
            dnevnoStanjeService.updateDS(novo);
            saRacuna.getDnevnaStanja().add(novo);
            racunService.saveRacun(saRacuna);
            System.out.println(saRacuna.getDnevnaStanja().size());
        }

        a.setStatus(Status.I);
        a.setDatumObrade(danas);
        analitikaIzvodaRepository.save(a);


	}
	
	public List<AnalitikaIzvoda> findAnalitikeForRacun(String brojRacuna){
		
		return analitikaIzvodaRepository.findByRacunNalogodavcaOrRacunPrimaoca(brojRacuna, brojRacuna);
	}
	
	public List<AnalitikaIzvoda> analitikeZaMedjubankarski(){
		
		return analitikaIzvodaRepository.findByMedjubankarskiAndStatus(true,  Status.E);
	}
}
