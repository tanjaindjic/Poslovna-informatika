package com.poslovna.poslovna.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Valuta {

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 3, max = 3)
    private String zvanicnaSifra;

    private String naziv;

    private boolean domicilna;

    @ManyToOne
    private Drzava drzava;

    @OneToMany
    private List<KursUValuti> kurseviOsnova;

    @OneToMany
    private List<KursUValuti> kurseviPrema;

    public Valuta() {
    }

    public Valuta(@Size(min = 3, max = 3) String zvanicnaSifra, String naziv, boolean domicilna, Drzava drzava, List<KursUValuti> kurseviOsnova, List<KursUValuti> kurseviPrema) {
        this.zvanicnaSifra = zvanicnaSifra;
        this.naziv = naziv;
        this.domicilna = domicilna;
        this.drzava = drzava;
        this.kurseviOsnova = kurseviOsnova;
        this.kurseviPrema = kurseviPrema;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZvanicnaSifra() {
        return zvanicnaSifra;
    }

    public void setZvanicnaSifra(String zvanicnaSifra) {
        this.zvanicnaSifra = zvanicnaSifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isDomicilna() {
        return domicilna;
    }

    public void setDomicilna(boolean domicilna) {
        this.domicilna = domicilna;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public List<KursUValuti> getKurseviOsnova() {
        return kurseviOsnova;
    }

    public void setKurseviOsnova(List<KursUValuti> kurseviOsnova) {
        this.kurseviOsnova = kurseviOsnova;
    }

    public List<KursUValuti> getKurseviPrema() {
        return kurseviPrema;
    }

    public void setKurseviPrema(List<KursUValuti> kurseviPrema) {
        this.kurseviPrema = kurseviPrema;
    }
}
