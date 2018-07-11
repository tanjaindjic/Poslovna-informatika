package com.poslovna.poslovna.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;


@Entity
@XmlRootElement(name="AnalitikaIzvoda")
public class AnalitikaIzvoda {

    @Id
    @GeneratedValue
    private long id;

    private String nalogodavac;

    private String svrhaPlacanja;

    private String primalac;

    private Date datumPrijema;

    private Date datumPlacanja;

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

    public AnalitikaIzvoda(String nalogodavac, String svrhaPlacanja, String primalac, Date datumPlacanja, Date datumObrade, Date datumValute,
                           @Size(max = 18, min = 5) String racunNalogodavca, int modelZaduzenja, @Size(max = 20) String pozivNaBroj,
                           @Size(max = 18, min = 5) String racunPrimaoca, int modelOdobrenja, boolean hitno, Float iznosZaPrenos, int tipGreske,
                           Status status, VrstaPlacanja vrstaPlacanja, boolean medjubankarski, NaseljenoMesto mestoPrijema, Valuta osnovnaValuta,
                           Float konvertovaniIznos,Valuta krajnjaValuta ) {
        this.nalogodavac = nalogodavac;
        this.svrhaPlacanja = svrhaPlacanja;
        this.primalac = primalac;
        this.datumPlacanja = datumPlacanja;
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

    //@XmlElement(name = "Id")
    public long getId() {
        return id;
    }

    @XmlTransient
    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "Nalogodavac")
    public String getNalogodavac() {
        return nalogodavac;
    }

    public void setNalogodavac(String nalogodavac) {
        this.nalogodavac = nalogodavac;
    }

    @XmlElement(name = "SvrhaPlacanja")
    public String getSvrhaPlacanja() {
        return svrhaPlacanja;
    }

    public void setSvrhaPlacanja(String svrhaPlacanja) {
        this.svrhaPlacanja = svrhaPlacanja;
    }

    @XmlElement(name = "Primalac")
    public String getPrimalac() {
        return primalac;
    }

    public void setPrimalac(String primalac) {
        this.primalac = primalac;
    }

    @XmlElement(name = "DatumPrijema")
    public Date getDatumPrijema() {
        return datumPrijema;
    }

    public void setDatumPrijema(Date datumPrijema) {
        this.datumPrijema = datumPrijema;
    }

    @XmlElement(name = "DatumValute")
    public Date getDatumValute() {
        return datumValute;
    }

    public void setDatumValute(Date datumValute) {
        this.datumValute = datumValute;
    }

    @XmlElement(name = "RacunNalogodavca")
    public String getRacunNalogodavca() {
        return racunNalogodavca;
    }

    public void setRacunNalogodavca(String racunNalogodavca) {
        this.racunNalogodavca = racunNalogodavca;
    }

    @XmlElement(name = "modelZaduzenja")
    public int getModelZaduzenja() {
        return modelZaduzenja;
    }

    public void setModelZaduzenja(int modelZaduzenja) {
        this.modelZaduzenja = modelZaduzenja;
    }

    @XmlElement(name = "PozivNaBroj")
    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }

    @XmlElement(name = "RacunPrimaoca")
    public String getRacunPrimaoca() {
        return racunPrimaoca;
    }
    
    public void setRacunPrimaoca(String racunPrimaoca) {
        this.racunPrimaoca = racunPrimaoca;
    }

    @XmlElement(name = "ModelOdobrenja")
    public int getModelOdobrenja() {
        return modelOdobrenja;
    }


    public void setModelOdobrenja(int modelOdobrenja) {
        this.modelOdobrenja = modelOdobrenja;
    }

    @XmlElement(name = "Hitno")
    public boolean isHitno() {
        return hitno;
    }


    public void setHitno(boolean hitno) {
        this.hitno = hitno;
    }

    @XmlElement(name = "Iznos")
    public Float getIznos() {
        return iznosZaPrenos;
    }

    public void setIznos(Float iznosZaPrenos) {
        this.iznosZaPrenos = iznosZaPrenos;
    }

    @XmlElement(name = "TipGreske")
    public int getTipGreske() {
        return tipGreske;
    }

    public void setTipGreske(int tipGreske) {
        this.tipGreske = tipGreske;
    }

    @XmlElement(name = "Status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @XmlElement(name = "VrstaPlacanja")
    public VrstaPlacanja getVrstaPlacanja() {
        return vrstaPlacanja;
    }

    public void setVrstaPlacanja(VrstaPlacanja vrstaPlacanja) {
        this.vrstaPlacanja = vrstaPlacanja;
    }

    @XmlElement(name = "Medjubankarski")
    public boolean isMedjubankarski() {
        return medjubankarski;
    }

    public void setMedjubankarski(boolean medjubankarski) {
        this.medjubankarski = medjubankarski;
    }

    @XmlElement(name = "OsnovnaValuta")
    public Valuta getValuta() {
        return osnovnaValuta;
    }

    public void setValuta(Valuta osnovnaValuta) {
        this.osnovnaValuta = osnovnaValuta;
    }

    @XmlElement(name = "MestoPrijema")
    public NaseljenoMesto getMestoPrijema() {
        return mestoPrijema;
    }

    public void setMestoPrijema(NaseljenoMesto mestoPrijema) {
        this.mestoPrijema = mestoPrijema;
    }

    @XmlElement(name = "DatumObrade")
    public Date getDatumObrade() {
        return datumObrade;
    }

    public void setDatumObrade(Date datumObrade) {
        this.datumObrade = datumObrade;
    }

    @XmlElement(name = "KonvertovaniIznos")
    public Float getKonvertovaniIznos() {
        return konvertovaniIznos;
    }

    public void setKonvertovaniIznos(Float konvertovaniIznos) {
        this.konvertovaniIznos = konvertovaniIznos;
    }

    @XmlElement(name = "KrajnjaValuta")
    public Valuta getKrajnjaValuta() {
        return krajnjaValuta;
    }

    public void setKrajnjaValuta(Valuta krajnjaValuta) {
        this.krajnjaValuta = krajnjaValuta;
    }

    @XmlElement(name = "DatumPlacanja")
    public Date getDatumPlacanja() {
        return datumPlacanja;
    }

    public void setDatumPlacanja(Date datumPlacanja) {
        this.datumPlacanja = datumPlacanja;
    }
}
