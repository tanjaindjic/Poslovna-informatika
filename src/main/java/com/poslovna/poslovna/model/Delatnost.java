package com.poslovna.poslovna.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Delatnost {

    @Id
    @GeneratedValue
    private long id;

    @Size(min=1, max = 4)
    private String sifra;

    private String naziv;

    @OneToMany
    private List<Klijent> klijenti;

    public Delatnost() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Klijent> getKlijenti() {
        return klijenti;
    }

    public void setKlijenti(List<Klijent> klijenti) {
        this.klijenti = klijenti;
    }
}
