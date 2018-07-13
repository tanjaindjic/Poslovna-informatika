package com.poslovna.poslovna.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaNalogodavcaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.exception.NemogucaKonverzijaValuteException;
import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.Banka;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.KursUValuti;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import com.poslovna.poslovna.repository.BankaRepository;
import com.poslovna.poslovna.repository.DnevnoStanjeRepository;
import com.poslovna.poslovna.repository.KursUValutiRepository;
import com.poslovna.poslovna.repository.RacunRepository;
import com.poslovna.poslovna.repository.ValutaRepository;

@Service
public class ImportExportService {
	
	@Autowired 
	private AnalitikaIzvodaRepository analitikaIzvodaRepository;
	
	@Autowired
	private RacunRepository racunRepository; 
	
	@Autowired
	private DnevnoStanjeRepository dnevnoStanjeRepository;
	
	@Autowired 
	private BankaRepository bankaRepository;
	
	@Autowired
	private KursUValutiRepository kursUValutiRepository;
	
	public boolean exportObjectToXml(Object nalog, Class classType, String id) {
		
		try {
			
			String uniquetime = new SimpleDateFormat("dd-MM-yyy_HH-mm-ss").format(new Date());
			String folderPath = "D:\\nalozi\\";
			
			File dir = new File(folderPath);
		    if (!dir.exists()) 
		    	dir.mkdirs();
			
			JAXBContext jContext = JAXBContext.newInstance(classType);
			Marshaller marshaller = jContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(nalog, new File(folderPath+"nalog_"+id+"_"+uniquetime+".xml"));
			
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public AnalitikaIzvoda importAnalitikaIzvoda(String filePath) {
		
		try {
			File file = new File("D:\\nalozi\\"+filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(AnalitikaIzvoda.class);

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			AnalitikaIzvoda analitikaIzvoda = (AnalitikaIzvoda) unmarshaller.unmarshal(file);

			return obradiImport(analitikaIzvoda);
		}catch(Exception e) {
			System.out.println("GRESKA: "+e.getMessage());
			//e.printStackTrace();
			return null;
		}

	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public AnalitikaIzvoda obradiImport(AnalitikaIzvoda nalog) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException, NemogucaKonverzijaValuteException{
		
		Banka banka = bankaRepository.findByBanka(true).get(0);
		
		Racun racunNalogodavca = racunRepository.findByBrojRacunaAndPoslovnaBanka(nalog.getRacunNalogodavca(), banka);
		Racun racunPrimaoca = racunRepository.findByBrojRacunaAndPoslovnaBanka(nalog.getRacunPrimaoca(), banka);
		
		if(racunNalogodavca == null && racunPrimaoca == null) {
			throw new NemaRacunaException("Ni jedan racun na nalogu ne pripada banci!");
		}
		
		DnevnoStanje dsN = null;
		DnevnoStanje dsP = null;
		float iznos = nalog.getIznos();
		nalog.setDatumPlacanja(null);
		
		// Interni Prenos
		if(racunNalogodavca != null && racunPrimaoca != null) {
			
			nalog.setMedjubankarski(false);
			nalog.setStatus(Status.I);
			nalog.setDatumPrijema(new Date(System.currentTimeMillis()));
			
			dsN = kreirajDnevnoStanje(racunNalogodavca);
			dsP = kreirajDnevnoStanje(racunPrimaoca);
			iznos = konvertujUValutu(racunNalogodavca, racunPrimaoca, iznos);
			
			if(proveriSredstva(dsN, racunNalogodavca, iznos)) {
				
				dsN.setPrometNaTeret(dsN.getPrometNaTeret()+iznos);
				dsN.setNovoStanje(dsN.getNovoStanje()-iznos);
				
				dsP.setPrometUKorist(dsP.getPrometUKorist()+konvertujUValutu(racunNalogodavca, racunPrimaoca, iznos));
				dsP.setNovoStanje(dsP.getNovoStanje()+konvertujUValutu(racunNalogodavca, racunPrimaoca, iznos));
				
				dnevnoStanjeRepository.save(dsN);
				dnevnoStanjeRepository.save(dsP);
				
				nalog.setDatumValute(new Date(System.currentTimeMillis()));
				nalog.setDatumObrade(new Date(System.currentTimeMillis()));
			}else{
				
				throw new NedovoljnoSredstavaException("Nalogodavac nema dovoljno sredstava na racunu.");
			}
			
			return analitikaIzvodaRepository.save(nalog);
		
		// Samo Nalogodavac
		}else if(racunNalogodavca != null && racunPrimaoca == null){
			
			nalog.setMedjubankarski(true);
			nalog.setStatus(Status.E);
			dsN = kreirajDnevnoStanje(racunNalogodavca);
			
			if(proveriSredstva(dsN, racunNalogodavca, iznos)) {
				
				if(nalog.isHitno()) {
					dsN.setPrometNaTeret(dsN.getPrometNaTeret()+iznos);
					dsN.setNovoStanje(dsN.getNovoStanje()-iznos);
					nalog.setStatus(Status.I);
					nalog.setDatumObrade(new Date(System.currentTimeMillis()));
				}
				
				return analitikaIzvodaRepository.save(nalog);
			}else{
				throw new NedovoljnoSredstavaException("Nalogodavac nema dovoljno sredstava na racunu.");
			}
			
		}else {
			nalog.setMedjubankarski(true);
			nalog.setStatus(Status.E);
			return analitikaIzvodaRepository.save(nalog);
		}
		
	}
	
	private DnevnoStanje kreirajDnevnoStanje(Racun racun) throws NemaRacunaException {
		
		if(racun != null) {
			if(!racun.isVazeci()) {
				throw new NemaRacunaException("Racun nije vazeci.");
			}else{
				
				List<DnevnoStanje> dnevnaStanja = dnevnoStanjeRepository.findByDatumPrometaAndZaRacun(new Date(System.currentTimeMillis()), racun);
				
				DnevnoStanje danasnje = dnevnaStanja.isEmpty() ? null : dnevnaStanja.get(0); 
			
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
	
	private boolean proveriSredstva(DnevnoStanje dnevnoStanje, Racun racun, float iznos) {
	
		float rezervisano = 0;
		
		List<AnalitikaIzvoda> rezSredstva = analitikaIzvodaRepository.findByRacunNalogodavcaAndStatus(racun.getBrojRacuna(), Status.E);
		
		if(!rezSredstva.isEmpty()) {
			for(AnalitikaIzvoda tempRez : rezSredstva) {
				rezervisano += tempRez.getIznos();
			}
		}
		
		if( (dnevnoStanje.getNovoStanje()-iznos-rezervisano) >= 0) {
			return true;
		}
		
		return false;
	}
	
	private float konvertujUValutu(Racun racunN, Racun racunP, float iznos) throws NemogucaKonverzijaValuteException {
		
		KursUValuti k = kursUValutiRepository.findByOsnovnaAndPrema(racunN.getValuta(), racunP.getValuta());
		if(k == null) {
			throw new NemogucaKonverzijaValuteException("Nemoguca konverzija zadate valute");
		}
		return iznos*k.getOdnos();
	}

}
