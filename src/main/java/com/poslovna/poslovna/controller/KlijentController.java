package com.poslovna.poslovna.controller;
import com.poslovna.poslovna.model.Klijent;
import com.poslovna.poslovna.model.enums.TipKlijenta;
import com.poslovna.poslovna.service.KlijentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value="/klijent")
public class KlijentController {

    @Autowired
    private KlijentService klijentService;

    @GetMapping(value = "/{id}")
    public Klijent getKlijentPrekoKorisnikID(@PathVariable Long id){
    	
    	System.out.println("ID JEE: "+id);
    	
    	Klijent nadjeno = klijentService.getKlijentPrekoKorisnikID(id);
    	
    	System.out.println(nadjeno);
    	
        return klijentService.getKlijentPrekoKorisnikID(id);
    }
    
    @RequestMapping(value = "/getKlijentsByTip/{tip}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Klijent>> getKlijentsByTip(@PathVariable String tip){
    	
    	System.out.println(tip);
    	
    	List<Klijent> retVal = null;
    	
    	if(tip.equals(TipKlijenta.F.toString())) {
    		retVal = klijentService.getKlijentsByTip(TipKlijenta.F);
    	}else if(tip.equals(TipKlijenta.P.toString())) {
    		retVal = klijentService.getKlijentsByTip(TipKlijenta.P);
    	}else {
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<List<Klijent>>(retVal, HttpStatus.OK);
    }
}
