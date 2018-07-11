package com.poslovna.poslovna.model;

import com.poslovna.poslovna.model.enums.TipKlijenta;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Klijent {

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private TipKlijenta tip;

    private String naziv;

    @Size(min = 9, max = 9)
    private String pib;
    
    @Size(min = 13, max = 13)
    private String jmbg;

    private String ime;

    private String prezime;

    private String adresa;

    private String email;

    private String fax;

    private String telefon;

    private String skraceni_naziv;

    private String nadlezni_organ;

    @ManyToOne(optional = false)
    private NaseljenoMesto prebivaliste;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Racun> racuni;

    @ManyToOne(optional = true)
    private Delatnost delatnost;

    public Klijent() {
    	
    }
    
    public Klijent(long id, TipKlijenta tip, String naziv, String pib, String ime, String prezime,
			String adresa, String email, String fax, String telefon, String skraceni_naziv, String nadlezni_organ,
			NaseljenoMesto prebivaliste, List<Racun> racuni, Delatnost delatnost, String jmbg) {
		super();
		this.id = id;
		this.tip = tip;
		this.naziv = naziv;
		this.pib = pib;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.email = email;
		this.fax = fax;
		this.telefon = telefon;
		this.skraceni_naziv = skraceni_naziv;
		this.nadlezni_organ = nadlezni_organ;
		this.prebivaliste = prebivaliste;
		this.racuni = racuni;
		this.delatnost = delatnost;
		this.jmbg = jmbg;
	}

	public Delatnost getDelatnost() {
		return delatnost;
	}

	public void setDelatnost(Delatnost delatnost) {
		this.delatnost = delatnost;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipKlijenta getTip() {
        return tip;
    }

    public void setTip(TipKlijenta tip) {
        this.tip = tip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSkraceni_naziv() {
        return skraceni_naziv;
    }

    public void setSkraceni_naziv(String skraceni_naziv) {
        this.skraceni_naziv = skraceni_naziv;
    }

    public String getNadlezni_organ() {
        return nadlezni_organ;
    }

    public void setNadlezni_organ(String nadlezni_organ) {
        this.nadlezni_organ = nadlezni_organ;
    }

    public NaseljenoMesto getPrebivaliste() {
        return prebivaliste;
    }

    public void setPrebivaliste(NaseljenoMesto prebivaliste) {
        this.prebivaliste = prebivaliste;
    }

    public List<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	@Override
	public String toString() {
		return "Klijent [id=" + id + ", tip=" + tip + ", naziv=" + naziv + ", pib=" + pib + ", jmbg=" + jmbg + ", ime="
				+ ime + ", prezime=" + prezime + ", adresa=" + adresa + ", email=" + email + ", fax=" + fax
				+ ", telefon=" + telefon + ", skraceni_naziv=" + skraceni_naziv + ", nadlezni_organ=" + nadlezni_organ
				+ ", prebivaliste=" + prebivaliste + ", racuni=" + racuni + ", delatnost=" + delatnost + "]";
	}
	
	
    
}
