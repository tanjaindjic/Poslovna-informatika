package com.poslovna.poslovna.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;
import java.util.List;

@Entity
@XmlRootElement(name="DnevnoStanje")
public class DnevnoStanje {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datumPrometa;

    private Float prethodnoStanje;

    private Float prometUKorist;

    private Float prometNaTeret;

    private Float novoStanje;

    @ManyToOne
    @JsonBackReference
    private Racun zaRacun;

    @ManyToMany
    private List<AnalitikaIzvoda> izvodi;

    public DnevnoStanje() {
    }
    
    public DnevnoStanje(Date datumPrometa, Float prethodnoStanje, Float prometUKorist, Float prometNaTeret,
			Float novoStanje, Racun zaRacun) {
		super();
		this.datumPrometa = datumPrometa;
		this.prethodnoStanje = prethodnoStanje;
		this.prometUKorist = prometUKorist;
		this.prometNaTeret = prometNaTeret;
		this.novoStanje = novoStanje;
		this.zaRacun = zaRacun;
	}

	public long getId() {
        return id;
    }

    @XmlTransient
    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "DatumPrometa")
    public Date getDatumPrometa() {
        return datumPrometa;
    }

    public void setDatumPrometa(Date datumPrometa) {
        this.datumPrometa = datumPrometa;
    }

    @XmlElement(name = "PrethodnoStanje")
    public Float getPrethodnoStanje() {
        return prethodnoStanje;
    }

    public void setPrethodnoStanje(Float prethodnoStanje) {
        this.prethodnoStanje = prethodnoStanje;
    }

    @XmlElement(name = "PrometUKorist")
    public Float getPrometUKorist() {
        return prometUKorist;
    }

    public void setPrometUKorist(Float prometUKorist) {
        this.prometUKorist = prometUKorist;
    }

    @XmlElement(name = "PrometNaTeret")
    public Float getPrometNaTeret() {
        return prometNaTeret;
    }

    public void setPrometNaTeret(Float prometNaTeret) {
        this.prometNaTeret = prometNaTeret;
    }

    @XmlElement(name = "NovoStanje")
    public Float getNovoStanje() {
        return novoStanje;
    }

    public void setNovoStanje(Float novoStanje) {
        this.novoStanje = novoStanje;
    }

    @XmlElement(name = "Racun")
    public Racun getZaRacun() {
        return zaRacun;
    }

    public void setZaRacun(Racun zaRacun) {
        this.zaRacun = zaRacun;
    }

    @XmlElement(name = "Izvodi")
    public List<AnalitikaIzvoda> getIzvodi() {
        return izvodi;
    }

    public void setIzvodi(List<AnalitikaIzvoda> izvodi) {
        this.izvodi = izvodi;
    }
}
