package com.poslovna.poslovna;

import com.poslovna.poslovna.model.*;
import com.poslovna.poslovna.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
}
