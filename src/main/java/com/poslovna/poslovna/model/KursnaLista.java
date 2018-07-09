package com.poslovna.poslovna.model;


import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class KursnaLista {

    @Id
    @GeneratedValue
    private long id;

    private Date datum;

    private int broj;

    private Date vaziOd;

    @ManyToOne
    private Banka zaBanku;

    @OneToMany
    private List<KursUValuti> kursevi;

    public KursnaLista() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public Date getVaziOd() {
        return vaziOd;
    }

    public void setVaziOd(Date vaziOd) {
        this.vaziOd = vaziOd;
    }

    public Banka getZaBanku() {
        return zaBanku;
    }

    public void setZaBanku(Banka zaBanku) {
        this.zaBanku = zaBanku;
    }

    public List<KursUValuti> getKursevi() {
        return kursevi;
    }

    public void setKursevi(List<KursUValuti> kursevi) {
        this.kursevi = kursevi;
    }
}
