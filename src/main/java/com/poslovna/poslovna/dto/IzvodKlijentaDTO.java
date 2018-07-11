package com.poslovna.poslovna.dto;

import java.sql.Date;

public class IzvodKlijentaDTO {
	private Date datumOd;
	private Date datumDo;
	private String brojRacuna;
	private String username;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getDatumOd() {
		return datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public String getBrojRacuna() {
		return brojRacuna;
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}
	public IzvodKlijentaDTO(){
		
	}
	
}
