package com.poslovna.poslovna.exportBeans;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.poslovna.poslovna.model.AnalitikaIzvoda;

@XmlRootElement(name="IzvodKlijenta")
public class AnalitikaIzvodaExport {

	private String brojRacuna;
	
	private Date pocetniDatum;
	
	private Date krajnjiDatum;
	
	private List<AnalitikaIzvoda> analitikeIzvoda;

	public AnalitikaIzvodaExport() {
		
	}

	public AnalitikaIzvodaExport(String brojRacuna, Date pocetniDatum, Date krajnjiDatum, List<AnalitikaIzvoda> analitikeIzvoda) {
		super();
		this.brojRacuna = brojRacuna;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.analitikeIzvoda = analitikeIzvoda;
	}

	@XmlElement(name = "PocetniDatum")
	public Date getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(Date pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	@XmlElement(name = "KrajnjiDatum")
	public Date getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(Date krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

	@XmlElement(name = "AnalitikeIzvoda")
	public List<AnalitikaIzvoda> getAnalitikeIzvoda() {
		return analitikeIzvoda;
	}

	public void setAnalitikeIzvoda(List<AnalitikaIzvoda> analitikeIzvoda) {
		this.analitikeIzvoda = analitikeIzvoda;
	}

	@XmlElement(name = "BrojRacuna")
	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}
	
	
}
