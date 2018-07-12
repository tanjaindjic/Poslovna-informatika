package com.poslovna.poslovna.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.dto.IzvodKlijentaDTO;
import com.poslovna.poslovna.exportBeans.AnalitikaIzvodaExport;
import com.poslovna.poslovna.exportBeans.MedjubankarskiExport;
import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.repository.KursUValutiRepository;
import com.poslovna.poslovna.service.AnalitikaIzvodaService;
import com.poslovna.poslovna.service.DnevnoStanjeService;
import com.poslovna.poslovna.service.ImportExportService;
import com.poslovna.poslovna.service.KlijentService;
import com.poslovna.poslovna.service.RacunService;

@RestController
@RequestMapping(value = "/impexp")
public class ImportExportController {

	@Autowired
	private ImportExportService importExportService;
	
	@Autowired
	private AnalitikaIzvodaService analitikaIzvodaService;
	
	@Autowired
	private DnevnoStanjeService dnevnoStanjeService;
	
	@Autowired
	private KlijentService klijentService;
	
	@Autowired
	private RacunService racunService;
	
	@Autowired
	private KursUValutiRepository kursUValutiRepository;
	
	@PreAuthorize("hasAnyAuthority('SLUZBENIK', 'KLIJENT')")
	@RequestMapping(value = "/exportAnalitikaIzvoda/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exoprtAnalitikaIzvoda(@PathVariable long id){
		
		AnalitikaIzvoda nalog = analitikaIzvodaService.getOne(id);
		
		if(nalog == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		boolean retVal = importExportService.exportObjectToXml(nalog, AnalitikaIzvoda.class, "id_"+nalog.getId());
		
		return new ResponseEntity<Boolean>(retVal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('SLUZBENIK', 'KLIJENT')")
	@RequestMapping(value = "/importAnalitikaIzvoda", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AnalitikaIzvoda> exoprtAnalitikaIzvoda(@RequestBody String filepath){
		
		AnalitikaIzvoda nalog = null;
		
		try {
			nalog = importExportService.importAnalitikaIzvoda(filepath);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<AnalitikaIzvoda>(HttpStatus.BAD_REQUEST);
		}
		
		if(nalog == null) {
			return new ResponseEntity<AnalitikaIzvoda>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<AnalitikaIzvoda>(nalog, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('SLUZBENIK', 'KLIJENT')")
	@RequestMapping(value = "/exportIzvod/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exoprtIzvoda(@PathVariable long id){
		
		DnevnoStanje dnevnoStanje = dnevnoStanjeService.getOne(id);
		
		if(dnevnoStanje == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		boolean retVal = importExportService.exportObjectToXml(dnevnoStanje, DnevnoStanje.class, "dnevno_"+dnevnoStanje.getId());
		
		return new ResponseEntity<Boolean>(retVal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('SLUZBENIK', 'KLIJENT')")
	@RequestMapping(value = "/exportZaDatume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exoprtZaDatume(@RequestBody IzvodKlijentaDTO parametri){
		
		Racun racun = racunService.findRacunByBroj(parametri.getBrojRacuna());
		
		if(racun == null) {
			new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<AnalitikaIzvoda> analitike = analitikaIzvodaService.findAnalitikeForRacun(racun.getBrojRacuna());
		
		AnalitikaIzvodaExport zaExport = new AnalitikaIzvodaExport(parametri.getBrojRacuna(), parametri.getDatumOd(), parametri.getDatumDo(), analitike);
		
		boolean retVal = importExportService.exportObjectToXml(zaExport, AnalitikaIzvodaExport.class, "racun_"+racun.getBrojRacuna());
		
		return new ResponseEntity<Boolean>(retVal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('SLUZBENIK')")
	@RequestMapping(value = "/exportMedjubankarski", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exoprtZaDatumeMedjubankarski(){
		
		List<AnalitikaIzvoda> analitike = analitikaIzvodaService.analitikeZaMedjubankarski();
		
		MedjubankarskiExport zaExport = new MedjubankarskiExport(new Date(System.currentTimeMillis()), analitike);
		
		boolean retVal = importExportService.exportObjectToXml(zaExport, MedjubankarskiExport.class, "medjubankarski");
		
		return new ResponseEntity<Boolean>(retVal, HttpStatus.OK);
	}
	
}
