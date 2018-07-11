package com.poslovna.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.poslovna.dto.IzvodKlijentaDTO;
import com.poslovna.poslovna.service.IzvestajService;

@RestController
@RequestMapping(value="/izvestaj")
public class IzvestajController {
	
	@Autowired
    private IzvestajService izvestajService;
	
	@RequestMapping(value = "/izvodKlijenta", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IzvodKlijentaDTO> getizvodKlijenta(@RequestBody IzvodKlijentaDTO izvodDTO){
    	
    	boolean file = izvestajService.getIzvodKlijenta(izvodDTO);
    	if(file)
    		return new ResponseEntity<>(izvodDTO, HttpStatus.OK);
    	return new ResponseEntity<>(izvodDTO, HttpStatus.BAD_REQUEST);
    	
    	
    }

}
