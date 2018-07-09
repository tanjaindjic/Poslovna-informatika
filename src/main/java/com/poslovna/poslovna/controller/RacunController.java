package com.poslovna.poslovna.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.Valuta;
import com.poslovna.poslovna.service.BankaService;
import com.poslovna.poslovna.service.KlijentService;
import com.poslovna.poslovna.service.RacunService;
import com.poslovna.poslovna.service.ValutaService;

import dto.RacunDTO;

@RestController
@RequestMapping(value="/racun")
public class RacunController {

    @Autowired
    private RacunService racunService;
    
    @Autowired
    private KlijentService klijentService;
    
    @Autowired
    private ValutaService valutaService;
    
    @Autowired
    private BankaService bankaService;
    
    @RequestMapping(value = "/saveRacun", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> sacuvajRacun(@RequestBody RacunDTO racunDTO){
    	
    	Klijent vlasnik = klijentService.getOne(racunDTO.getVlasnikId());
    	
    	if(!racunService.checkIfUnique(racunDTO.getBrojRacuna())) {
    		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    	}
    	
    	if(vlasnik == null) {
    		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    	}
    	
    	Valuta valuta = valutaService.getOne(racunDTO.getValutaId().longValue());
    	
    	if(valuta == null) {
    		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    	}
    	
    	Racun noviRacun = new Racun(racunDTO.getBrojRacuna(), new Date(System.currentTimeMillis()), true, vlasnik, bankaService.getFirst(), null, valuta, null);
    	vlasnik.getRacuni().add(noviRacun);
    	noviRacun = racunService.saveRacun(noviRacun);
    	klijentService.saveKlinet(vlasnik);
    	
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@RequestMapping(value = "/getRacuniNum/{pagenum}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Racun>> gratiRacunePaginacija(@PathVariable int pagenum){
    	
    	if(pagenum < 1) {
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<Page<Racun>>(racunService.getRacuniByPage(new PageRequest(pagenum-1, 5)), HttpStatus.OK);
    }
}
