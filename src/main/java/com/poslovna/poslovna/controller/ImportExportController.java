package com.poslovna.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.model.AnalitikaIzvoda;
import com.poslovna.poslovna.repository.AnalitikaIzvodaRepository;
import com.poslovna.poslovna.service.AnalitikaIzvodaService;
import com.poslovna.poslovna.service.ImportExportService;

@RestController
@RequestMapping(value = "/impexp")
public class ImportExportController {

	@Autowired
	private ImportExportService importExportService;
	
	@Autowired
	private AnalitikaIzvodaService analitikaIzvodaService;
	
	
	@RequestMapping(value = "/exportAnalitikaIzvoda/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exoprtAnalitikaIzvoda(@PathVariable long id){
		
		AnalitikaIzvoda nalog = analitikaIzvodaService.getOne(id);
		
		if(nalog == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		boolean retVal = importExportService.exoprtAnalitikaIzvoda(nalog);
		
		return new ResponseEntity<Boolean>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/importAnalitikaIzvoda/{filepath}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AnalitikaIzvoda> exoprtAnalitikaIzvoda(@PathVariable String filepath){
		
		AnalitikaIzvoda nalog = importExportService.importAnalitikaIzvoda(filepath);
		
		if(nalog == null) {
			return new ResponseEntity<AnalitikaIzvoda>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<AnalitikaIzvoda>(nalog, HttpStatus.OK);
	}
	
}
