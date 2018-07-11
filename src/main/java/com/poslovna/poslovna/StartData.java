package com.poslovna.poslovna;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.poslovna.poslovna.model.*;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poslovna.poslovna.model.enums.TipKlijenta;
import com.poslovna.poslovna.model.enums.TipKorisnika;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import com.poslovna.poslovna.repository.BankaRepository;
import com.poslovna.poslovna.repository.DelatnostRepository;
import com.poslovna.poslovna.repository.DnevnoStanjeRepository;
import com.poslovna.poslovna.repository.DrzavaRepository;
import com.poslovna.poslovna.repository.KlijentRepository;
import com.poslovna.poslovna.repository.KorisnikRepository;
import com.poslovna.poslovna.repository.KursUValutiRepository;
import com.poslovna.poslovna.repository.KursnaListaRepository;
import com.poslovna.poslovna.repository.NaseljenoMestoRepository;
import com.poslovna.poslovna.repository.RacunRepository;
import com.poslovna.poslovna.repository.SluzbenikRepository;
import com.poslovna.poslovna.repository.UkidanjeRepository;
import com.poslovna.poslovna.repository.ValutaRepository;

@Component
public class StartData {
    @Autowired
    private DrzavaRepository drzavaRepository;
    @Autowired
    private BankaRepository bankaRepository;
    @Autowired
    private NaseljenoMestoRepository naseljenoMestoRepository;
    @Autowired
    private ValutaRepository valutaRepository;
    @Autowired
    private RacunRepository racunRepository;
    @Autowired
    private KlijentRepository klijentRepository;
    @Autowired
    private DelatnostRepository delatnostRepository;
    @Autowired
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;
    @Autowired
    private DnevnoStanjeRepository dnevnoStanjeRepository;
    @Autowired
    private KursnaListaRepository kursnaListaRepository;
    @Autowired
    private UkidanjeRepository ukidanjeRepository;
    @Autowired
    private KursUValutiRepository kursUValutiRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private SluzbenikRepository sluzbenikRepository;

    private int i ;

    @PostConstruct
    private void init(){
        Drzava srbija = createDrzava(new ArrayList<NaseljenoMesto>(), "Srbija", "SRB");
        Drzava bosna = createDrzava(new ArrayList<NaseljenoMesto>(), "Bosna i Hercegovina", "BIH");
        Drzava hrvatska = createDrzava(new ArrayList<NaseljenoMesto>(), "Hrvatska", "HRV");
        Drzava makedonija = createDrzava(new ArrayList<NaseljenoMesto>(), "Republika Makedonija", "MKD");
        Drzava amerika = createDrzava(new ArrayList<NaseljenoMesto>(), "Sjedinjene Americke drzave", "USD");

        NaseljenoMesto novisad = createNaseljenoMesto("Novi Sad", "21000", srbija);
        NaseljenoMesto beograd = createNaseljenoMesto("Beograd", "11000", srbija);
        NaseljenoMesto sarajevo = createNaseljenoMesto("Sarajevo", "71000", bosna);
        NaseljenoMesto zagreb = createNaseljenoMesto("Zagreb", "10000", hrvatska);
        NaseljenoMesto skopje = createNaseljenoMesto("Skopje", "1000", makedonija);
        NaseljenoMesto njujork = createNaseljenoMesto("New York", "11004–05", amerika);
        
        Delatnost delatnost1 = createDelatnost("DE1", "Ugostiteljstvo");
        Delatnost delatnost2 = createDelatnost("DE2", "Gradjevinarstvo");
        Delatnost delatnost3 = createDelatnost("DE3", "Poljoprivreda");
        
        Korisnik korisnik1 = createKorisnik("theMika", "mmmmmmmm", null, null, TipKorisnika.KLIJENT);
        Korisnik korisnik2 = createKorisnik("theZika", "zzzzzzzz", null, null, TipKorisnika.KLIJENT);
        Korisnik korisnik3 = createKorisnik("theAna", "aaaaaaaa", null, null, TipKorisnika.KLIJENT);
        Korisnik korisnik4 = createKorisnik("markovic", "markovic", null, null, TipKorisnika.KLIJENT);
        Korisnik korisnik5 = createKorisnik("theCeca", "cccccccc", null, null, TipKorisnika.SLUZBENIK);
        
        Klijent klijent = createKlijent(korisnik1.getId(), TipKlijenta.F, null, null, "Mika", "Mikic", "Adresa 1 bb", "mikamikic@gmail.com", "+38165123321", "+38165123321", null, null, novisad, new ArrayList<>(), null, "0102982800011");
        Klijent klijent2 = createKlijent(korisnik2.getId(), TipKlijenta.F, null, null, "Zika", "Zikic", "Adresa 2 bb", "zikazikic@gmail.com", null, "+38165456654", null, null, beograd, new ArrayList<>(), null, "0102982800012");
        Klijent klijent3 = createKlijent(korisnik3.getId(), TipKlijenta.F, null, null, "Ana", "Petrovic", "Adresa 3 bb", "anap@gmail.com", null, "+38165258852", null, null, skopje, new ArrayList<>(), null, "0103971800013");
        Klijent klijent4 = createKlijent(korisnik4.getId(), TipKlijenta.P, "Preduzece Markovic", "123456789", "Marko", "Markovic", "Adresa 4 bb", "markovic@gmail.com", null, "+385888999", "PRM", null, zagreb, new ArrayList<>(), delatnost1, null);
        
        Sluzbenik sluzbenik = ceateSluzbenik(korisnik5.getId(), "Ceca", "Petrovic");
        
        korisnik1.setKlijent(klijent);
        korisnik2.setKlijent(klijent2);
        korisnik3.setKlijent(klijent3);
        korisnik4.setKlijent(klijent4);
        korisnik5.setSluzbenik(sluzbenik);
        
        korisnikRepository.save(korisnik1);
        korisnikRepository.save(korisnik2);
        korisnikRepository.save(korisnik3);
        korisnikRepository.save(korisnik4);
        korisnikRepository.save(korisnik5);
        
        Banka srpskaBanka = createBanka("123", "123321123", "Srpska banka", beograd);
        Banka vojvodjanskaBanka = createBanka("400", "123451123", "Vojvodjanska banka", beograd);

        Valuta dinar = createValulta("RSD", "Srpski dinar", true, srbija);
        Valuta dolar = createValulta("USD", "Americki dolar", false, amerika);
        Valuta euro  = createValulta("EUR", "Evro", false, null);

        KursnaLista k = createKursnaLista(srpskaBanka);

        k.getKursevi().add(createKursUValuti(dinar, dinar, 1F, k));
        k.getKursevi().add(createKursUValuti(euro, euro, 1F, k));
        k.getKursevi().add(createKursUValuti(dolar, dolar, 1F, k));
        k.getKursevi().add(createKursUValuti(dinar, euro, 0.0085F, k));
        k.getKursevi().add(createKursUValuti(dinar, dolar, 0.0100F, k));
        k.getKursevi().add(createKursUValuti(euro, dinar, 118.03F, k));
        k.getKursevi().add(createKursUValuti(euro, dolar, 1.18F, k));
        k.getKursevi().add(createKursUValuti(dolar, dinar, 100.21F, k));
        k.getKursevi().add(createKursUValuti(dolar, euro, 0.85F, k));

        kursnaListaRepository.save(k);

        createRacun("123000001", klijent, srpskaBanka, dinar);
        createRacun("123000002", klijent, srpskaBanka, dolar);
        createRacun("123000003", klijent, srpskaBanka, euro);
        createRacun("123000004", klijent2, srpskaBanka, dinar);
        createRacun("123000005", klijent2, srpskaBanka, dolar);
        createRacun("123000006", klijent2, srpskaBanka, euro);
        createRacun("123000007", klijent3, srpskaBanka, dinar);
        createRacun("123000008", klijent3, srpskaBanka, dolar);
        createRacun("123000009", klijent3, srpskaBanka, euro);
        createRacun("400000001", klijent4, vojvodjanskaBanka, dinar);
        createRacun("400000002", klijent4, vojvodjanskaBanka, dolar);
        createRacun("400000003", klijent4, vojvodjanskaBanka, euro);

        i = 0;
        AnalitikaIzvoda a = createNalog(klijent, beograd, dinar, klijent2, 1000F);
        AnalitikaIzvoda a2 = createNalog(klijent, beograd, dinar, klijent3, 1500F);
        AnalitikaIzvoda a3 = createNalog(klijent2, beograd, dinar, klijent4, 500F);
        AnalitikaIzvoda a4 = createNalog(klijent4, beograd, dinar, klijent3, 81F);
        AnalitikaIzvoda a5 = createNalog(klijent4, beograd, dinar, klijent, 230F);
        AnalitikaIzvoda a6 = createNalog(klijent, beograd, dinar, klijent2, 150F);
        AnalitikaIzvoda a7 = createNalog(klijent3, beograd, dinar, klijent, 50F);
        AnalitikaIzvoda a8 = createNalog(klijent2, beograd, dinar, klijent4, 9000F);

        klijentRepository.save(klijent);
        klijentRepository.save(klijent2);
        klijentRepository.save(klijent3);
        klijentRepository.save(klijent4);

        for(Klijent kl : klijentRepository.findAll())
            for (Racun r : kl.getRacuni()) {
                r.getDnevnaStanja().add(initDnevnoStanje(r));
                racunRepository.save(r);
            }

    }

    private KursnaLista createKursnaLista(Banka banka) {
        KursnaLista k = new KursnaLista();
        k.setBroj(1);
        k.setDatum(new Date(System.currentTimeMillis()));
        k.setKursevi(new ArrayList<>());
        k.setVaziOd(new Date(System.currentTimeMillis()));
        k.setZaBanku(banka);
        kursnaListaRepository.save(k);
        return k;
    }

    private KursUValuti createKursUValuti(Valuta od, Valuta ka, Float odnos, KursnaLista kursnaLista) {
        KursUValuti k = new KursUValuti();
        k.setOdnos(odnos);
        k.setOsnovna(od);
        k.setPrema(ka);
        k.setPripada(kursnaLista);
        kursUValutiRepository.save(k);

        return  k;
    }

    private AnalitikaIzvoda createNalog(Klijent nalogodavac, NaseljenoMesto mesto, Valuta valuta, Klijent primalac, Float iznos) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -i);
        AnalitikaIzvoda a = new AnalitikaIzvoda(nalogodavac.getIme() + nalogodavac.getPrezime(), "Uplata na racun",
                primalac.getIme() + primalac.getPrezime(), new Date(now.getTimeInMillis()), null, new Date(now.getTimeInMillis()),
                nalogodavac.getRacuni().get(0).getBrojRacuna(), 97, "123-2050531-2", primalac.getRacuni().get(0).getBrojRacuna(), 97, false, iznos, 0, Status.E, VrstaPlacanja.GOTOVINSKO, false, mesto, valuta, iznos, primalac.getRacuni().get(0).getValuta());
        a.setDatumPrijema(new Date(now.getTimeInMillis()));
        analitikaIzvodaRepository.save(a);
        i++;
        return a;
    }

    private Valuta createValulta(String sifra, String naziv, boolean domicilna, Drzava drzava) {
        return valutaRepository.save(new Valuta(sifra, naziv, domicilna, drzava, new ArrayList<>(), new ArrayList<>()));
    }

    private NaseljenoMesto createNaseljenoMesto(String naziv, String ptt, Drzava drzava){
        NaseljenoMesto novo = new NaseljenoMesto();
        novo.setNaziv(naziv);
        novo.setPtt(ptt);
        novo.setDrzava(drzava);
        naseljenoMestoRepository.save(novo);
        drzava.getNaseljenaMesta().add(novo);
        drzavaRepository.save(drzava);
        return novo;
    }

    private Drzava createDrzava(List<NaseljenoMesto> naseljenaMesta, String naziv, String sifra){
        Drzava nova = new Drzava();
        nova.setNaseljenaMesta(naseljenaMesta);
        nova.setNaziv(naziv);
        nova.setSifra_drzave(sifra);
        drzavaRepository.save(nova);
        return nova;
    }
    
   private Korisnik createKorisnik(String korisnickoIme, String lozinka, Sluzbenik sluzbenik, Klijent klijent, TipKorisnika tip) {
	   
	   return korisnikRepository.save(new Korisnik(korisnickoIme, lozinka, klijent, sluzbenik, tip));
   }
   
   private Klijent createKlijent(long id, TipKlijenta tip, String naziv, String pib, String ime, String prezime,
			String adresa, String email, String fax, String telefon, String skraceni_naziv, String nadlezni_organ,
			NaseljenoMesto prebivaliste, List<Racun> racuni, Delatnost delatnost, String jmbg) {
	   
	   return klijentRepository.save(new Klijent(id, tip, naziv, pib, ime, prezime, adresa, email, fax, telefon, skraceni_naziv, nadlezni_organ, prebivaliste, racuni, delatnost, jmbg));
   }

   private Banka createBanka(String sifra_banke, String pib, String naziv, NaseljenoMesto naseljenoMesto){
        return bankaRepository.save(new Banka(sifra_banke, pib, naziv, "Savska 14", "srpskabanka@srpb.rs", "www.srpskabanka.rs",
                "+381112366658", "+381112366658", true, naseljenoMesto));
   }
   
   private Sluzbenik ceateSluzbenik(long id, String ime, String prezime) {
	   
	   return sluzbenikRepository.save(new Sluzbenik(id, ime, prezime));
   }

   private void createRacun(String br, Klijent k, Banka b, Valuta v){
        
        Racun r = new Racun(br, new Date(System.currentTimeMillis()), true, k, b, new ArrayList<>(), v,new ArrayList<>() );
        racunRepository.save(r);
        k.getRacuni().add(r);
   }
   
   private Delatnost createDelatnost(String sifra, String naziv) {
	   
	   return delatnostRepository.save(new Delatnost(sifra, naziv));
   }

   private DnevnoStanje initDnevnoStanje(Racun racun){
        DnevnoStanje ds = new DnevnoStanje();
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -8);
        ds.setDatumPrometa(new java.sql.Date(now.getTimeInMillis()));
        ds.setPrethodnoStanje(10000F);
        ds.setPrometNaTeret(0F);
        ds.setPrometUKorist(0F);
        ds.setNovoStanje(10000F);
        ds.setZaRacun(racun);
        ds.setIzvodi(new ArrayList<>());
        //ds.setIzvodi(analitikaIzvodaRepository.findByRacunNalogodavca(racun.getBrojRacuna()));
        //ds.getIzvodi().addAll(analitikaIzvodaRepository.findByRacunPrimaoca(racun.getBrojRacuna()));
        dnevnoStanjeRepository.save(ds);
        return ds;
   }
   
}
