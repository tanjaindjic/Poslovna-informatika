package com.poslovna.poslovna.exportBeans;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.poslovna.poslovna.model.AnalitikaIzvoda;

@XmlRootElement(name="MedjubankarskiTransfer")
public class MedjubankarskiExport {

	private Date datumExporta;
	
	private List<AnalitikaIzvoda> analitikeIzvoda;

	public MedjubankarskiExport() {
		super();
	}

	public MedjubankarskiExport(Date datumExporta, List<AnalitikaIzvoda> analitikeIzvoda) {
		super();
		this.datumExporta = datumExporta;
		this.analitikeIzvoda = analitikeIzvoda;
	}

	@XmlElement(name = "DatumExporta")
	public Date getDatumExporta() {
		return datumExporta;
	}

	public void setDatumExporta(Date datumExporta) {
		this.datumExporta = datumExporta;
	}

	@XmlElement(name = "AnalitikaIzvoda")
	public List<AnalitikaIzvoda> getAnalitikeIzvoda() {
		return analitikeIzvoda;
	}

	public void setAnalitikeIzvoda(List<AnalitikaIzvoda> analitikeIzvoda) {
		this.analitikeIzvoda = analitikeIzvoda;
	}
	
	
}
