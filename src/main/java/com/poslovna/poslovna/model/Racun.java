package com.poslovna.poslovna.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Racun {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    @Size(min=5, max=18)
    private String brojRacuna;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datumOtvaranja;

    @Column(nullable = false)
    private boolean vazeci;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Klijent vlasnik;

    @ManyToOne(optional = false)
    private Banka poslovnaBanka;

    @OneToMany
    private List<Ukidanje> ukidanje;

    @ManyToOne(optional = false)
    private Valuta valuta;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DnevnoStanje> dnevnaStanja;

    public Racun() {
    }

    public Racun(@Size(min = 5, max = 18) String brojRacuna, Date datumOtvaranja, boolean vazeci, Klijent vlasnik, Banka poslovnaBanka, List<Ukidanje> ukidanje, Valuta valuta, List<DnevnoStanje> dnevnaStanja) {
        this.brojRacuna = brojRacuna;
        this.datumOtvaranja = datumOtvaranja;
        this.vazeci = vazeci;
        this.vlasnik = vlasnik;
        this.poslovnaBanka = poslovnaBanka;
        this.ukidanje = ukidanje;
        this.valuta = valuta;
        this.dnevnaStanja = dnevnaStanja;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public Date getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(Date datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public boolean isVazeci() {
        return vazeci;
    }

    public void setVazeci(boolean vazeci) {
        this.vazeci = vazeci;
    }

    public Klijent getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Klijent vlasnik) {
        this.vlasnik = vlasnik;
    }

    public Banka getPoslovnaBanka() {
        return poslovnaBanka;
    }

    public void setPoslovnaBanka(Banka poslovnaBanka) {
        this.poslovnaBanka = poslovnaBanka;
    }

    public List<Ukidanje> getUkidanje() {
        return ukidanje;
    }

    public void setUkidanje(List<Ukidanje> ukidanje) {
        this.ukidanje = ukidanje;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public List<DnevnoStanje> getDnevnaStanja() {
        return dnevnaStanja;
    }

    public void setDnevnaStanja(List<DnevnoStanje> dnevnaStanja) {
        this.dnevnaStanja = dnevnaStanja;
    }
}
