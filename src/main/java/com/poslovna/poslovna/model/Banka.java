package com.poslovna.poslovna.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Banka {
    @Id
    @GeneratedValue
    private long id;

    @Size(min = 3, max = 3)
    private String sifra_banke;

    @Size(min = 9, max = 9)
    private String PIB;

    private String naziv;

    private String adresa;

    private String email;

    private String web;

    private String telefon;

    private String fax;

    private boolean banka;

    public Banka() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSifra_banke() {
        return sifra_banke;
    }

    public void setSifra_banke(String sifra_banke) {
        this.sifra_banke = sifra_banke;
    }

    public String getPIB() {
        return PIB;
    }

    public void setPIB(String PIB) {
        this.PIB = PIB;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public boolean isBanka() {
        return banka;
    }

    public void setBanka(boolean banka) {
        this.banka = banka;
    }
}
