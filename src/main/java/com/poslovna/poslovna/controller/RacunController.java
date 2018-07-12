package com.poslovna.poslovna.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

import com.poslovna.poslovna.dto.AnalitikaIzvodaDTO;
import com.poslovna.poslovna.dto.RacunDTO;
import com.poslovna.poslovna.exception.NedovoljnoSredstavaException;
import com.poslovna.poslovna.exception.NemaNalogodavcaException;
import com.poslovna.poslovna.exception.NemaRacunaException;
import com.poslovna.poslovna.model.DnevnoStanje;
import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.Racun;
import com.poslovna.poslovna.model.Valuta;
import com.poslovna.poslovna.service.AnalitikaIzvodaService;
import com.poslovna.poslovna.service.BankaService;
import com.poslovna.poslovna.service.DnevnoStanjeService;
import com.poslovna.poslovna.service.KlijentService;
import com.poslovna.poslovna.service.RacunService;
import com.poslovna.poslovna.service.ValutaService;

@RestController
@RequestMapping(value="/racun")
public class RacunController {

    @Autowired
    private RacunService racunService;
    
    @Autowired
    private KlijentService klijentService;

    @Autowired
	private DnevnoStanjeService dnevnoStanjeService;

    @Autowired
	private AnalitikaIzvodaService analitikaIzvodaService;
    
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
    public ResponseEntity<Page<Racun>> vratiRacunePaginacija(@PathVariable int pagenum){
    	
    	if(pagenum < 1) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<Page<Racun>>(racunService.getRacuniByPage(new PageRequest(pagenum-1, 5)), HttpStatus.OK);
    }

	@RequestMapping(value = "/svi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Racun>> vratiRacune(){
    	
    	return new ResponseEntity<List<Racun>>(racunService.vratiRacune(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/aktiviraj", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> aktivirajRacun(@RequestBody Racun racun){
    	
		if(racunService.aktiviraj(racun) == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
    	
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
	@RequestMapping(value = "/deaktiviraj/{naRacun}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deaktivirajRacun(@PathVariable String naRacun, @RequestBody Racun racun){
    	
		if(racunService.deaktiviraj(racun, naRacun) == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
    	
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
	@RequestMapping(value = "/brojRacuna/{brojRacuna}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Klijent> vratiVlasnika1(@PathVariable String brojRacuna){
    	Klijent k = klijentService.findKlijentByRacun(brojRacuna);
		if(k == null) {
			return new ResponseEntity<Klijent>(new Klijent(), HttpStatus.BAD_REQUEST);
		}
    	
    	return new ResponseEntity<Klijent>(k, HttpStatus.OK);
    }

	@RequestMapping(value = "/gasenje/{brojRacunaZaPrenos}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void ugasi(@PathVariable String brojRacunaZaPrenos, @RequestBody Racun zaGasenje) throws NedovoljnoSredstavaException, NemaNalogodavcaException, NemaRacunaException {
    	//kliring da se odradi za taj racun
        Racun primalac = racunService.findRacunByBroj(brojRacunaZaPrenos);
        if(primalac!=null && !primalac.isVazeci()){
            System.out.println("nevazeci racun!");
            return;
        }
		dnevnoStanjeService.kliringZaGasenje(zaGasenje);
		DnevnoStanje zadnje = Collections.max(zaGasenje.getDnevnaStanja(), Comparator.comparing(c -> c.getDatumPrometa()));
		AnalitikaIzvodaDTO nalog = new AnalitikaIzvodaDTO();
		nalog.setDatumPlacanja(new java.sql.Date(System.currentTimeMillis()));
		nalog.setHitno(true);
		nalog.setIznos(zadnje.getNovoStanje());
		nalog.setKlijentId(klijentService.findKlijentByRacun(zaGasenje.getBrojRacuna()).getId());
		nalog.setModelOdobrenja(97);
		nalog.setModelZaduzenja(97);
		nalog.setRacunNalogodavca(zaGasenje.getBrojRacuna());
		nalog.setSvrhaPlacanja("Gasenje racuna - prenos sredstava");
		nalog.setRacunPrimaoca(brojRacunaZaPrenos);
		Racun update = racunService.findRacunByBroj(zaGasenje.getBrojRacuna());
		update.setVazeci(false);
		racunService.saveRacun(update);

	}
}
