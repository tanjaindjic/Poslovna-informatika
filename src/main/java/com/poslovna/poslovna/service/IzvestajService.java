package com.poslovna.poslovna.service;

import java.sql.DriverManager;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.Connection;
import com.poslovna.poslovna.dto.IzvodKlijentaDTO;
import com.poslovna.poslovna.repository.BankaRepository;
import com.poslovna.poslovna.repository.KlijentRepository;

@Service
public class IzvestajService {
	
	
	@Autowired
    private KlijentRepository klijentRepo;
	
	@Autowired
    private BankaRepository bankaRepo;
	
	public boolean getIzvodKlijenta(IzvodKlijentaDTO izvodDTO) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("korisnicko_ime", izvodDTO.getUsername());
		params.put("datum_od", izvodDTO.getDatumOd());
		params.put("broj_racuna", izvodDTO.getBrojRacuna());
		params.put("datum_do", izvodDTO.getDatumDo());
	
		try {
			String path = System.getProperty("user.dir");
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/poslovnaInformatika","root","root");
			JasperReport jasperReport = JasperCompileManager.compileReport(path+"\\izvodKlijenta.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
			JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\nalozi\\izvod_"+izvodDTO.getBrojRacuna()+".pdf");
			return true;
		}catch (Exception ex) {
				ex.printStackTrace();
			}
		return false;
	}

	public boolean getIzvodBanke(int id) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("banka_id", id);
	
		try {
			String path = System.getProperty("user.dir");
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/poslovnaInformatika","root","root");
			JasperReport jasperReport = JasperCompileManager.compileReport(path+"\\spisakRacuna.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
			JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\nalozi\\izvod_"+bankaRepo.getOne(new Long(id)).getNaziv()+".pdf");
			return true;
		}catch (Exception ex) {
				ex.printStackTrace();
			}
		return false;
	}
	
	
}
