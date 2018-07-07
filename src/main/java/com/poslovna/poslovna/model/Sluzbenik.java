package com.poslovna.poslovna.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sluzbenik {
	
	@Id
	private long id;
	
	@Column(nullable = false)
	private String ime;

	@Column(nullable = false)
    private String prezime;
    
    public Sluzbenik() {
    	
    }
    
	public Sluzbenik(long id, String ime, String prezime) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
    
    

}
