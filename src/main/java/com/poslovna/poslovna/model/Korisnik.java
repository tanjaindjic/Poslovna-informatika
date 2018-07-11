package com.poslovna.poslovna.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.poslovna.poslovna.model.enums.TipKorisnika;


@Entity
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false, unique = true)
	private String korisnickoIme;
	
	@Column(nullable = false)
	private String lozinka;

	@OneToOne(optional = true)
	@PrimaryKeyJoinColumn
    private Klijent klijent;

	@OneToOne(optional = true)
	@PrimaryKeyJoinColumn
    private Sluzbenik sluzbenik;
	
	@Enumerated(EnumType.STRING)
	private TipKorisnika tip;
	
	public Korisnik() {
		
	}

	public Korisnik(String korisnickoIme, String lozinka, Klijent klijent, Sluzbenik sluzbenik, TipKorisnika tip) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.klijent = klijent;
		this.sluzbenik = sluzbenik;
		this.tip = tip;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public Sluzbenik getSluzbenik() {
		return sluzbenik;
	}

	public void setSluzbenik(Sluzbenik sluzbenik) {
		this.sluzbenik = sluzbenik;
	}

	public TipKorisnika getTip() {
		return tip;
	}

	public void setTip(TipKorisnika tip) {
		this.tip = tip;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
}
