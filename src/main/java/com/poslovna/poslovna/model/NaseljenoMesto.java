package com.poslovna.poslovna.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="NaseljenoMesto")
public class NaseljenoMesto {
    @Id
    @GeneratedValue
    private long id;

    private String naziv;

    private String ptt;

    @ManyToOne
    private Drzava drzava;

    
    public NaseljenoMesto() {
    	
    }
    
    @XmlElement(name="Drzava")
    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    @XmlElement(name="Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name="Naziv")
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlElement(name="Ptt")
    public String getPtt() {
        return ptt;
    }

    public void setPtt(String ptt) {
        this.ptt = ptt;
    }
}
