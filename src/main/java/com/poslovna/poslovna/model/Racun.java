package com.poslovna.poslovna.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;


@Entity
public class Racun {

    @Id
    @GeneratedValue
    private long id;

    @Size(min=5, max=18)
    private String brojRacuna;

    private Date datumOtvaranja;

    private boolean vazeci;

    @ManyToOne
    private Klijent vlasnik;

    @ManyToOne
    private Banka poslovnaBanka;

    @ManyToOne
    private Ukidanje ukidanje;

    @ManyToOne
    private Valuta valuta;

    @OneToMany
    private List<DnevnoStanje> dnevnaStanja;

    public Racun() {
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

    public Ukidanje getUkidanje() {
        return ukidanje;
    }

    public void setUkidanje(Ukidanje ukidanje) {
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
