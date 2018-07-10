package com.poslovna.poslovna.dto;

import com.poslovna.poslovna.model.NaseljenoMesto;
import com.poslovna.poslovna.model.Valuta;
import com.poslovna.poslovna.model.enums.Status;
import com.poslovna.poslovna.model.enums.VrstaPlacanja;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.util.Date;

public class AnalitikaIzvodaDTO {

    private String nalogodavac;

    private String svrhaPlacanja;

    private String primalac;

    private Date datumPlacanja;

    @Size(max = 18, min = 5)
    private String racunNalogodavca;

    private int modelZaduzenja;

    @Size(max = 20)
    private String pozivNaBroj;

    @Size(max = 18, min = 5)
    private String racunPrimaoca;

    private int modelOdobrenja;

    private boolean hitno;

    private Float iznos;


    private Long klijentId;

    public AnalitikaIzvodaDTO() {
    }

    public AnalitikaIzvodaDTO(String nalogodavac, String svrhaPlacanja, String primalac, Date datumPrijema, @Size(max = 18, min = 5) String racunNalogodavca, int modelZaduzenja, @Size(max = 20) String pozivNaBroj, @Size(max = 18, min = 5) String racunPrimaoca, int modelOdobrenja, boolean hitno, Float iznos,  Long klijentId) {
        this.nalogodavac = nalogodavac;
        this.svrhaPlacanja = svrhaPlacanja;
        this.primalac = primalac;
        this.datumPlacanja = datumPlacanja;
        this.racunNalogodavca = racunNalogodavca;
        this.modelZaduzenja = modelZaduzenja;
        this.pozivNaBroj = pozivNaBroj;
        this.racunPrimaoca = racunPrimaoca;
        this.modelOdobrenja = modelOdobrenja;
        this.hitno = hitno;
        this.iznos = iznos;
        this.klijentId = klijentId;
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

    public Date getDatumPlacanja() {
        return datumPlacanja;
    }

    public void setDatumPlacanja(Date datumPlacanja) {
        this.datumPlacanja = datumPlacanja;
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
        return iznos;
    }

    public void setIznos(Float iznos) {
        this.iznos = iznos;
    }

    public Long getKlijentId() {
        return klijentId;
    }

    public void setKlijentId(Long klijentId) {
        this.klijentId = klijentId;
    }
}
