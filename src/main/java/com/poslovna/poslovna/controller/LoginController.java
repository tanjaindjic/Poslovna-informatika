package com.poslovna.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.security.TokenUtils;
import com.poslovna.poslovna.securityBeans.CustomUserDetailsFactory;
import com.poslovna.poslovna.service.KorisnikService;

@RestController
@RequestMapping(value="rest/")
public class LoginController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> testMethod(@RequestBody Korisnik forLogin){
	   	
	    Korisnik korisnik = korisnikService.getKorisnikByKorisnickoIme(forLogin.getKorisnickoIme());
	    
	    if(korisnik == null) {
	    	return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	    }
	    
	    if(!korisnik.getLozinka().equals(forLogin.getLozinka())) {
	    	return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	    }
	    
	    String token = tokenUtils.generateToken(CustomUserDetailsFactory.createCustomUserDetails(korisnik));
	    
	  	return new ResponseEntity<String>(token, HttpStatus.OK);
	}

}
