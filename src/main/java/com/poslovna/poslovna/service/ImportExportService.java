package com.poslovna.poslovna.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.AnalitikaIzvoda;

@Service
public class ImportExportService {
	
	public boolean exoprtAnalitikaIzvoda(AnalitikaIzvoda nalog) {
		
		try {
			
			String uniquetime = new SimpleDateFormat("dd-MM-yyy_HH-mm-ss").format(new Date());
			String folderPath = "D:\\nalozi\\";
			
			File dir = new File(folderPath);
		    if (!dir.exists()) 
		    	dir.mkdirs();
			
			JAXBContext jContext = JAXBContext.newInstance(AnalitikaIzvoda.class);
			Marshaller marshaller = jContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(nalog, new File(folderPath+"nalog_id"+nalog.getId()+"_"+uniquetime+".xml"));
			
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public AnalitikaIzvoda importAnalitikaIzvoda(String filePath) {
		
		try {
			File file = new File("D:\\nalozi\\nalog_id1_11-07-2018_16-25-38.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(AnalitikaIzvoda.class);

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			AnalitikaIzvoda analitikaIzvoda = (AnalitikaIzvoda) unmarshaller.unmarshal(file);
			
			return analitikaIzvoda;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
