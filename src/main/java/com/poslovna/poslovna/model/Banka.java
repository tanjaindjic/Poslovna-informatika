package com.poslovna.poslovna.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Banka {
    @Id
    @GeneratedValue
    private long id;

    @Size(min = 3, max = 3)
    private String sifraBanke;

    @Size(min = 9, max = 9)
    private String PIB;

    private String naziv;

    private String adresa;

    private String email;

    private String web;

    private String telefon;

    private String fax;

    private boolean banka;

    @ManyToOne
    private NaseljenoMesto naseljenoMesto;

    public Banka() {
    }

    public Banka(@Size(min = 3, max = 3) String sifraBanke, @Size(min = 9, max = 9) String PIB, String naziv, String adresa, String email, String web, String telefon, String fax, boolean banka, NaseljenoMesto naseljenoMesto) {
        this.sifraBanke = sifraBanke;
        this.PIB = PIB;
        this.naziv = naziv;
        this.adresa = adresa;
        this.email = email;
        this.web = web;
        this.telefon = telefon;
        this.fax = fax;
        this.banka = banka;
        this.naseljenoMesto = naseljenoMesto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSifraBanke() {
        return sifraBanke;
    }

    public void setSifraBanke(String sifraBanke) {
        this.sifraBanke = sifraBanke;
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

    public NaseljenoMesto getNaseljenoMesto() {
        return naseljenoMesto;
    }

    public void setNaseljenoMesto(NaseljenoMesto naseljenoMesto) {
        this.naseljenoMesto = naseljenoMesto;
    }
}
