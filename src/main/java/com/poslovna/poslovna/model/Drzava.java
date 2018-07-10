package com.poslovna.poslovna.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@XmlRootElement(name="Drzava")
public class Drzava {

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 1, max = 3)
    private String sifra_drzave;

    private String naziv;

    @JsonBackReference
    @ManyToMany
    private List<NaseljenoMesto> naseljenaMesta;

    public Drzava() {
    	
    }
    
    public List<NaseljenoMesto> getNaseljenaMesta() {
        return naseljenaMesta;
    }

    public void setNaseljenaMesta(List<NaseljenoMesto> naseljenaMesta) {
        this.naseljenaMesta = naseljenaMesta;
    }

    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement
    public String getSifra_drzave() {
        return sifra_drzave;
    }

    public void setSifra_drzave(String sifra_drzave) {
        this.sifra_drzave = sifra_drzave;
    }

    @XmlElement
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
