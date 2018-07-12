package com.poslovna.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.dto.AnalitikaIzvodaDTO;
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
	
	
	@RequestMapping(value = "/exportAnalitikaIzvoda/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exoprtAnalitikaIzvoda(@PathVariable long id){
		
		AnalitikaIzvoda nalog = analitikaIzvodaService.getOne(id);
		
		if(nalog == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		boolean retVal = importExportService.exportObjectToXml(nalog, AnalitikaIzvoda.class, "id_"+nalog.getId());
		
		return new ResponseEntity<Boolean>(retVal, HttpStatus.OK);
	}
	
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
	
	@RequestMapping(value = "/exportIzvod/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exoprtIzvoda(@PathVariable long id){
		
		DnevnoStanje dnevnoStanje = dnevnoStanjeService.getOne(id);
		
		if(dnevnoStanje == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		boolean retVal = importExportService.exportObjectToXml(dnevnoStanje, DnevnoStanje.class, "dnevno_"+dnevnoStanje.getId());
		
		return new ResponseEntity<Boolean>(retVal, HttpStatus.OK);
	}
	
}
