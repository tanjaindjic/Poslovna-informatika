package com.poslovna.poslovna;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
        
        Klijent klijent1 = createKlijent(TipKlijenta.F, null, null, "Mika", "Mikic", "Adresa 1 bb", "mikamikic@gmail.com", null, "+38165123321", null, null, novisad, new ArrayList<>(), null, "0101985800011");
        Klijent klijent2 = createKlijent(TipKlijenta.F, null, null, "Zika", "Zikic", "Adresa 2 bb", "zikazikic@gmail.com", null, "+38165456654", null, null, beograd, new ArrayList<>(), null, "0102982800012");
        Klijent klijent3 = createKlijent(TipKlijenta.F, null, null, "Ana", "Petrovic", "Adresa 3 bb", "anap@gmail.com", null, "+38165258852", null, null, skopje, new ArrayList<>(), null, "0103971800013");
        Klijent klijent4 = createKlijent(TipKlijenta.P, "Preduzece Markovic", "123456789", "Marko", "Markovic", "Adresa 4 bb", "markovic@gmail.com", null, "+385888999", "PRM", null, zagreb, new ArrayList<>(), delatnost1, null);
        Sluzbenik sluzbenik = ceateSluzbenik("Ceca", "Petrovic");		
        
        Korisnik korisnik1 = createKorisnik("theMika", "mmmmmmmm", null, klijent1, TipKorisnika.KLIJENT);
        Korisnik korisnik2 = createKorisnik("theZika", "zzzzzzzz", null, klijent2, TipKorisnika.KLIJENT);
        Korisnik korisnik3 = createKorisnik("theAna", "aaaaaaaa", null, klijent3, TipKorisnika.KLIJENT);
        Korisnik korisnik4 = createKorisnik("markovic", "markovic", null, klijent4, TipKorisnika.KLIJENT);
        Korisnik korisnik5 = createKorisnik("theCeca", "cccccccc", sluzbenik, null, TipKorisnika.SLUZBENIK);

        Banka srpskaBanka = createBanka("123", "123321123", "Srpska banka", beograd);
        Banka vojvodjanskaBanka = createBanka("124", "123451123", "Vojvodjanska banka", beograd);

        Valuta dinar = createValulta("RSD", "Srpski dinar", true, srbija);
        Valuta kuna = createValulta("HRK", "Hrvatska kuna", false, hrvatska);
        Valuta marka = createValulta("BAM", "Konvertibilna marka", false, bosna);
        Valuta dolar = createValulta("USD", "Americki dolar", false, amerika);
        Valuta euro  = createValulta("EUR", "Evro", false, null);

        createRacun("123456789", klijent1, srpskaBanka, dinar);
        createRacun("685138522", klijent1, srpskaBanka, dolar);
        createRacun("852952201", klijent1, srpskaBanka, euro);

        AnalitikaIzvoda a = createNalog(klijent1, beograd, euro);

        klijentRepository.save(klijent1);


    }

    private AnalitikaIzvoda createNalog(Klijent k, NaseljenoMesto mesto, Valuta valuta) {
        AnalitikaIzvoda a = new AnalitikaIzvoda("theMika", "sam sebi uplacujem", "the Mika", new Date(System.currentTimeMillis()), null, new Date(System.currentTimeMillis()),
                "123456789", 97, "123-2050531-2", "685138522", 97, false, 10000F, 0, Status.E, VrstaPlacanja.GOTOVINSKO, false, mesto, valuta);
        analitikaIzvodaRepository.save(a);
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
   
   private Klijent createKlijent(TipKlijenta tip, String naziv, String pib, String ime, String prezime,
			String adresa, String email, String fax, String telefon, String skraceni_naziv, String nadlezni_organ,
			NaseljenoMesto prebivaliste, List<Racun> racuni, Delatnost delatnost, String jmbg) {
	   
	   return klijentRepository.save(new Klijent(tip, naziv, pib, ime, prezime, adresa, email, fax, telefon, skraceni_naziv, nadlezni_organ, prebivaliste, racuni, delatnost, jmbg));
   }

   private Banka createBanka(String sifra_banke, String pib, String naziv, NaseljenoMesto naseljenoMesto){
        return bankaRepository.save(new Banka(sifra_banke, pib, naziv, "Savska 14", "srpskabanka@srpb.rs", "www.srpskabanka.rs",
                "+381112366658", "+381112366658", true, naseljenoMesto));
   }
   
   private Sluzbenik ceateSluzbenik(String ime, String prezime) {
	   
	   return sluzbenikRepository.save(new Sluzbenik(ime, prezime));
   }

   private void createRacun(String br, Klijent k, Banka b, Valuta v){
        Ukidanje u = ukidanjeRepository.save(new Ukidanje());
        Racun r = new Racun(br, new Date(System.currentTimeMillis()), true, k, b, u, v,new ArrayList<>() );
        racunRepository.save(r);
        k.getRacuni().add(r);
   }
   
   private Delatnost createDelatnost(String sifra, String naziv) {
	   
	   return delatnostRepository.save(new Delatnost(sifra, naziv));
   }
   
}
