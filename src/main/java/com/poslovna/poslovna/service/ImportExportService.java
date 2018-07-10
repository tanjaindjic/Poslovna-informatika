package com.poslovna.poslovna.service;

import java.io.File;

import javax.xml.bind.*;
import javax.xml.transform.Result;

import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.AnalitikaIzvoda;

@Service
public class ImportExportService {
	
	public boolean exoprtAnalitikaIzvoda(AnalitikaIzvoda nalog) {
		
		try {
			
			JAXBContext jContext = JAXBContext.newInstance(AnalitikaIzvoda.class);
			Marshaller marshaller = jContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(nalog, (Result)System.in);
			marshaller.marshal(nalog, new File("C:\\Users\\nalozi\\"+nalog.getId()+".xml"));
			return true;
			
		}catch(Exception e) {
			return false;
		}
	}

}
