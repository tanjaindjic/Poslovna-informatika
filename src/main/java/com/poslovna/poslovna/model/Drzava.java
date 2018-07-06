package com.poslovna.poslovna.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Drzava {

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 1, max = 3)
    private String sifra_drzave;

    private String naziv;

    @ManyToMany
    private List<NaseljenoMesto> naseljenaMesta;

    public List<NaseljenoMesto> getNaseljenaMesta() {
        return naseljenaMesta;
    }

    public void setNaseljenaMesta(List<NaseljenoMesto> naseljenaMesta) {
        this.naseljenaMesta = naseljenaMesta;
    }

    public Drzava() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSifra_drzave() {
        return sifra_drzave;
    }

    public void setSifra_drzave(String sifra_drzave) {
        this.sifra_drzave = sifra_drzave;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
