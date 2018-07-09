package com.poslovna.poslovna.model;

import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
public class AnalitikaIzvoda {

    @Id
    @GeneratedValue
    private long id;

    private String nalogodavac;

    private String svrhaPlacanja;

    private String primalac;

    private Date datumPrijema;

    private Date datumObrade;

    private Date datumValute;

    @Size(max = 18, min = 5)
    private String racunNalogodavca;

    private int modelZaduzenja;

    @Size(max = 20)
    private String pozivNaBroj;

    @Size(max = 18, min = 5)
    private String racunPrimaoca;

    private int modelOdobrenja;

    private boolean hitno;

    private Float iznosZaPrenos;

    private int tipGreske;

    private Status status;

    @Enumerated(EnumType.STRING)
    private VrstaPlacanja vrstaPlacanja;

    private boolean medjubankarski;

    @ManyToOne
    private NaseljenoMesto mestoPrijema;

    @ManyToOne
    private Valuta osnovnaValuta;

    private Float konvertovaniIznos;

    @ManyToOne
    private Valuta krajnjaValuta;

    public AnalitikaIzvoda() {
    }

    public AnalitikaIzvoda(String nalogodavac, String svrhaPlacanja, String primalac, Date datumPrijema, Date datumObrade, Date datumValute,
                           @Size(max = 18, min = 5) String racunNalogodavca, int modelZaduzenja, @Size(max = 20) String pozivNaBroj,
                           @Size(max = 18, min = 5) String racunPrimaoca, int modelOdobrenja, boolean hitno, Float iznosZaPrenos, int tipGreske,
                           Status status, VrstaPlacanja vrstaPlacanja, boolean medjubankarski, NaseljenoMesto mestoPrijema, Valuta osnovnaValuta,
                           Float konvertovaniIznos,Valuta krajnjaValuta ) {
        this.nalogodavac = nalogodavac;
        this.svrhaPlacanja = svrhaPlacanja;
        this.primalac = primalac;
        this.datumPrijema = datumPrijema;
        this.datumObrade = datumObrade;
        this.datumValute = datumValute;
        this.racunNalogodavca = racunNalogodavca;
        this.modelZaduzenja = modelZaduzenja;
        this.pozivNaBroj = pozivNaBroj;
        this.racunPrimaoca = racunPrimaoca;
        this.modelOdobrenja = modelOdobrenja;
        this.hitno = hitno;
        this.iznosZaPrenos = iznosZaPrenos;
        this.tipGreske = tipGreske;
        this.status = status;
        this.vrstaPlacanja = vrstaPlacanja;
        this.medjubankarski = medjubankarski;
        this.mestoPrijema = mestoPrijema;
        this.osnovnaValuta = osnovnaValuta;
        this.konvertovaniIznos = konvertovaniIznos;
        this.krajnjaValuta = krajnjaValuta;
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

    public String getRacunPrimaoca() {
        return racunPrimaoca;
    }

    public void setRacunPrimaoca(String racunPrimaoca) {
        this.racunPrimaoca = racunPrimaoca;
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
        return iznosZaPrenos;
    }

    public void setIznos(Float iznosZaPrenos) {
        this.iznosZaPrenos = iznosZaPrenos;
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
        return osnovnaValuta;
    }

    public void setValuta(Valuta osnovnaValuta) {
        this.osnovnaValuta = osnovnaValuta;
    }

    public NaseljenoMesto getMestoPrijema() {
        return mestoPrijema;
    }

    public void setMestoPrijema(NaseljenoMesto mestoPrijema) {
        this.mestoPrijema = mestoPrijema;
    }

    public Date getDatumObrade() {
        return datumObrade;
    }

    public void setDatumObrade(Date datumObrade) {
        this.datumObrade = datumObrade;
    }

    public Float getKonvertovaniIznos() {
        return konvertovaniIznos;
    }

    public void setKonvertovaniIznos(Float konvertovaniIznos) {
        this.konvertovaniIznos = konvertovaniIznos;
    }

    public Valuta getKrajnjaValuta() {
        return krajnjaValuta;
    }

    public void setKrajnjaValuta(Valuta krajnjaValuta) {
        this.krajnjaValuta = krajnjaValuta;
    }
}
