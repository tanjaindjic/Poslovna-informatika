package com.poslovna.poslovna.model;

import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
public class AnalitikaIzvoda {

    @Id
    private long id;

    private String nalogodavac;

    private String svrhaPlacanja;

    private String primalac;

    private Date datumPrijema;

    private Date datumValute;

    @Size(max = 18, min = 5)
    private String racunNalogodavca;

    @Size(min = 2, max = 2)
    private int modelZaduzenja;

    @Size(max = 20)
    private String pozivNaBroj;

    @Size(max = 18, min = 5)
    private String racunPoverioca;

    @Size(min = 2, max = 2)
    private int modelOdobrenja;

    private boolean hitno;

    private Float iznos;

    private int tipGreske;

    private Status status;

    @Enumerated(EnumType.STRING)
    private VrstaPlacanja vrstaPlacanja;

    private boolean medjubankarski;

    private NaseljenoMesto mestoPrijema;

    @ManyToOne
    private Valuta valuta;

    public AnalitikaIzvoda() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNalogodavac() {
        return nalogodavac;
    }

    public void setNalogodavac(String nalogodavac) {
        this.nalogodavac = nalogodavac;
    }

    public String getSvrhaPlacanja() {
        return svrhaPlacanja;
    }

    public void setSvrhaPlacanja(String svrhaPlacanja) {
        this.svrhaPlacanja = svrhaPlacanja;
    }

    public String getPrimalac() {
        return primalac;
    }

    public void setPrimalac(String primalac) {
        this.primalac = primalac;
    }

    public Date getDatumPrijema() {
        return datumPrijema;
    }

    public void setDatumPrijema(Date datumPrijema) {
        this.datumPrijema = datumPrijema;
    }

    public Date getDatumValute() {
        return datumValute;
    }

    public void setDatumValute(Date datumValute) {
        this.datumValute = datumValute;
    }

    public String getRacunNalogodavca() {
        return racunNalogodavca;
    }

    public void setRacunNalogodavca(String racunNalogodavca) {
        this.racunNalogodavca = racunNalogodavca;
    }

    public int getModelZaduzenja() {
        return modelZaduzenja;
    }

    public void setModelZaduzenja(int modelZaduzenja) {
        this.modelZaduzenja = modelZaduzenja;
    }

    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }

    public String getRacunPoverioca() {
        return racunPoverioca;
    }

    public void setRacunPoverioca(String racunPoverioca) {
        this.racunPoverioca = racunPoverioca;
    }

    public int getModelOdobrenja() {
        return modelOdobrenja;
    }

    public void setModelOdobrenja(int modelOdobrenja) {
        this.modelOdobrenja = modelOdobrenja;
    }

    public boolean isHitno() {
        return hitno;
    }

    public void setHitno(boolean hitno) {
        this.hitno = hitno;
    }

    public Float getIznos() {
        return iznos;
    }

    public void setIznos(Float iznos) {
        this.iznos = iznos;
    }

    public int getTipGreske() {
        return tipGreske;
    }

    public void setTipGreske(int tipGreske) {
        this.tipGreske = tipGreske;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public VrstaPlacanja getVrstaPlacanja() {
        return vrstaPlacanja;
    }

    public void setVrstaPlacanja(VrstaPlacanja vrstaPlacanja) {
        this.vrstaPlacanja = vrstaPlacanja;
    }

    public boolean isMedjubankarski() {
        return medjubankarski;
    }

    public void setMedjubankarski(boolean medjubankarski) {
        this.medjubankarski = medjubankarski;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public NaseljenoMesto getMestoPrijema() {
        return mestoPrijema;
    }

    public void setMestoPrijema(NaseljenoMesto mestoPrijema) {
        this.mestoPrijema = mestoPrijema;
    }
}