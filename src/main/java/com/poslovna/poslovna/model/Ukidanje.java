package com.poslovna.poslovna.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity
public class Ukidanje {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datumUkidanja;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Racun racun;

    @Column(nullable = false)
    private String prenosNaRacun;

	public Ukidanje() {
		super();
	}

	public Ukidanje(Date datumUkidanja, Racun racun, String prenosNaRacun) {
		super();
		this.datumUkidanja = datumUkidanja;
		this.racun = racun;
		this.prenosNaRacun = prenosNaRacun;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDatumUkidanja() {
		return datumUkidanja;
	}

	public void setDatumUkidanja(Date datumUkidanja) {
		this.datumUkidanja = datumUkidanja;
	}

	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	public String getPrenosNaRacun() {
		return prenosNaRacun;
	}

	public void setPrenosNaRacun(String prenosNaRacun) {
		this.prenosNaRacun = prenosNaRacun;
	}
    
}
