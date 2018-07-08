package com.poslovna.poslovna;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poslovna.poslovna.model.Delatnost;
import com.poslovna.poslovna.model.Drzava;
import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.model.NaseljenoMesto;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.Sluzbenik;
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

        NaseljenoMesto novisad = createNaseljenoMesto("Novi Sad", "21000", srbija);
        NaseljenoMesto beograd = createNaseljenoMesto("Beograd", "11000", srbija);
        NaseljenoMesto sarajevo = createNaseljenoMesto("Sarajevo", "71000", bosna);
        NaseljenoMesto zagreb = createNaseljenoMesto("Zagreb", "10000", hrvatska);
        NaseljenoMesto skopje = createNaseljenoMesto("Skopje", "1000", makedonija);
        
        Klijent klijent = createKlijent(TipKlijenta.F, null, null, "Mika", "Mikic", "Adresa 1 bb", "mikamikic@gmail.com", null, "+38165123321", null, null, novisad, null, null);
        Sluzbenik sluzbenik = ceateSluzbenik("Ceca", "Petrovic");		
        
        Korisnik korisnik1 = createKorisnik("theMika", "mmmmmmmm", null, klijent, TipKorisnika.KLIJENT);
        Korisnik korisnik2 = createKorisnik("theCeca", "cccccccc", sluzbenik, null, TipKorisnika.SLUZBENIK);

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
			NaseljenoMesto prebivaliste, List<Racun> racuni, Delatnost delatnost) {
	   
	   return klijentRepository.save(new Klijent(tip, naziv, pib, ime, prezime, adresa, email, fax, telefon, skraceni_naziv, nadlezni_organ, prebivaliste, racuni, delatnost));
   }
   
   private Sluzbenik ceateSluzbenik(String ime, String prezime) {
	   
	   return sluzbenikRepository.save(new Sluzbenik(ime, prezime));
   }
   
   
}
