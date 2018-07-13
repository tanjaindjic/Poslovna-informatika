package com.poslovna.poslovna.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.exception.NemogucaKonverzijaValuteException;
import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.Banka;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.KursUValuti;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.Ukidanje;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import com.poslovna.poslovna.repository.BankaRepository;
import com.poslovna.poslovna.repository.DnevnoStanjeRepository;
import com.poslovna.poslovna.repository.KursUValutiRepository;
import com.poslovna.poslovna.repository.RacunRepository;
import com.poslovna.poslovna.repository.UkidanjeRepository;

@Service
public class RacunService {
	
	@Autowired
	private RacunRepository racunRepository;
	
	@Autowired 
	private AnalitikaIzvodaRepository analitikaIzvodaRepository;
	
	@Autowired
	private UkidanjeRepository ukidanjeRepository;
	
	@Autowired
	private BankaRepository bankaRepository;
	
	@Autowired
	private DnevnoStanjeRepository dnevnoStanjeRepository;
	
	@Autowired
	private KursUValutiRepository kursUValutiRepository;
	
	public Racun saveRacun(Racun racun) {
		
		return racunRepository.save(racun);
	}
	
	public boolean checkIfUnique(String brojRacuna){
		
		return racunRepository.findByBrojRacuna(brojRacuna) == null ? true : false;
	}
	
	public List<Racun> getAllRacuni(){
		
		return racunRepository.findAll();
	}
	
	public Page<Racun> getRacuniByPage(Pageable pageable){
		
		return racunRepository.findAll(pageable);
	}
	
	public Racun aktiviraj(Racun racun) {
		
		if(racun == null) {
			return null;
		}
		
		Racun retVal = racunRepository.getOne(racun.getId());
		retVal.setVazeci(true);
		
		return racunRepository.save(retVal);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Racun deaktiviraj(Racun racun, String naRacun) throws NedovoljnoSredstavaException, NemogucaKonverzijaValuteException, NemaRacunaException {
		
		Banka banka = bankaRepository.findByBanka(true).get(0);
		
		if(racun == null) {
			throw new NemaRacunaException("Nepostojeci broj racuna.");
		}
		
		Racun zaGasenje = racunRepository.getOne(racun.getId());
		zaGasenje.setVazeci(false);
		
		DnevnoStanje dnevno = dnevnoStanjeRepository.findTopByZaRacunOrderByDatumPrometaDesc(racun);
		
		float sredstva = izracunajSredstva(dnevno, zaGasenje);
		
		if(sredstva < 0.00f) {
			throw new NedovoljnoSredstavaException("Stanje racuna nije ispravno.");
		}
		
		Racun zaUplatu = racunRepository.findByBrojRacunaAndPoslovnaBanka(naRacun, banka);
		
		AnalitikaIzvoda nalog = null;
		
		//Interni Prenos
		if(zaUplatu != null) {
			
			nalog = new AnalitikaIzvoda(zaGasenje.getVlasnik().getIme()+" "+zaGasenje.getVlasnik().getPrezime(), "Uplata prilikom gasenja racuna.", zaUplatu.getVlasnik().getIme()+" "+zaUplatu.getVlasnik().getPrezime(), 
					new Date(System.currentTimeMillis()), null, new Date(System.currentTimeMillis()), zaGasenje.getBrojRacuna(), 97, null, zaUplatu.getBrojRacuna(), 97, sredstva > 250000f ? true : false, 
							sredstva, 0, Status.I, VrstaPlacanja.GOTOVINSKO, false, banka.getNaseljenoMesto(), zaGasenje.getValuta(), sredstva, zaUplatu.getValuta());
			
			DnevnoStanje dnevnoStanjePrimaoca = kreirajDnevnoStanje(zaUplatu);
			
			dnevno.setPrometNaTeret(dnevno.getPrometNaTeret()+sredstva);
			dnevno.setNovoStanje(dnevno.getNovoStanje()-sredstva);
			
			dnevnoStanjePrimaoca.setPrometUKorist(dnevnoStanjePrimaoca.getPrometUKorist()+konvertujUValutu(zaGasenje, zaUplatu, sredstva));
			dnevnoStanjePrimaoca.setNovoStanje(dnevnoStanjePrimaoca.getNovoStanje()+konvertujUValutu(zaGasenje, zaUplatu, sredstva));
			
			dnevnoStanjeRepository.save(dnevno);
			dnevnoStanjeRepository.save(dnevnoStanjePrimaoca);
			
			nalog = analitikaIzvodaRepository.save(nalog);
			
		//Medjubankarski
		}else{
			
			nalog = new AnalitikaIzvoda(zaGasenje.getVlasnik().getIme()+" "+zaGasenje.getVlasnik().getPrezime(), "Uplata prilikom gasenja racuna.", "Primalac", 
					new Date(System.currentTimeMillis()), null, null, zaGasenje.getBrojRacuna(), 97, null, naRacun, 97, sredstva > 250000f ? true : false, 
							sredstva, 0, Status.E, VrstaPlacanja.GOTOVINSKO, false, banka.getNaseljenoMesto(), zaGasenje.getValuta(), sredstva, zaGasenje.getValuta());
			
			dnevno.setPrometNaTeret(dnevno.getPrometNaTeret()+sredstva);
			dnevno.setNovoStanje(dnevno.getNovoStanje()-sredstva);
			
			dnevnoStanjeRepository.save(dnevno);
			nalog = analitikaIzvodaRepository.save(nalog);
		}
		
		Ukidanje ukidanje = new Ukidanje(new Date(System.currentTimeMillis()), zaGasenje, naRacun);
		ukidanjeRepository.save(ukidanje);
		
		return racunRepository.save(zaGasenje);
	}

    public Racun findRacunByBroj(String racunPrimaoca) {
		//ovo izmeniti kad se promeni da metoda vraca samo 1 racun
		return racunRepository.findByBrojRacuna(racunPrimaoca);
    }

	public List<Racun> vratiRacune() {
		// TODO Auto-generated method stub
		return racunRepository.findAll();
	}
	
	private float izracunajSredstva(DnevnoStanje dnevno, Racun racun) {
		
		if(dnevno == null || racun == null) {
			return 0.00f;
		}
		
		List<AnalitikaIzvoda> rezSredstva = analitikaIzvodaRepository.findByRacunNalogodavcaAndStatus(racun.getBrojRacuna(), Status.E);
		
		float rezervisano = 0.00f;
		
		if(!rezSredstva.isEmpty()) {
			for(AnalitikaIzvoda tempRez : rezSredstva) {
				rezervisano += tempRez.getIznos();
			}
		}
		
		return dnevno.getNovoStanje()-rezervisano;
	}
	
	private DnevnoStanje kreirajDnevnoStanje(Racun racun) throws NemaRacunaException {
		
		if(racun != null) {
			if(!racun.isVazeci()) {
				throw new NemaRacunaException("Racun nije vazeci.");
			}else{
				DnevnoStanje danasnje = dnevnoStanjeRepository.findByDatumPrometaAndZaRacun(new Date(System.currentTimeMillis()), racun);
				
				if(danasnje == null){
					DnevnoStanje prethodno = dnevnoStanjeRepository.findTopByZaRacunOrderByDatumPrometaDesc(racun);
					return dnevnoStanjeRepository.save(new DnevnoStanje(new Date(System.currentTimeMillis()), new Float(prethodno.getNovoStanje()), new Float(0.00), new Float(0.00), new Float(prethodno.getNovoStanje()), racun));
				}else{
					return danasnje;
				}
			}
		}
		
		return null;
	}
	
	private float konvertujUValutu(Racun racunN, Racun racunP, float iznos) throws NemogucaKonverzijaValuteException {
		
		KursUValuti k = kursUValutiRepository.findByOsnovnaAndPrema(racunN.getValuta(), racunP.getValuta());
		if(k == null) {
			throw new NemogucaKonverzijaValuteException("Nemoguca konverzija zadate valute");
		}
		return iznos*k.getOdnos();
	}
}
