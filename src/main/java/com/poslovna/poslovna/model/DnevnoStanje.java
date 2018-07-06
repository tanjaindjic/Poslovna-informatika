package com.poslovna.poslovna.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class DnevnoStanje {

    @Id
    @GeneratedValue
    private long id;

    private Date datumPrometa;

    private Float prethodnoStanje;

    private Float prometUKorist;

    private Float prometNaTeret;

    private Float novoStanje;

    @ManyToOne
    private Racun zaRacun;

    @ManyToMany
    private List<AnalitikaIzvoda> izvodi;

    public DnevnoStanje() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDatumPrometa() {
        return datumPrometa;
    }

    public void setDatumPrometa(Date datumPrometa) {
        this.datumPrometa = datumPrometa;
    }

    public Float getPrethodnoStanje() {
        return prethodnoStanje;
    }

    public void setPrethodnoStanje(Float prethodnoStanje) {
        this.prethodnoStanje = prethodnoStanje;
    }

    public Float getPrometUKorist() {
        return prometUKorist;
    }

    public void setPrometUKorist(Float prometUKorist) {
        this.prometUKorist = prometUKorist;
    }

    public Float getPrometNaTeret() {
        return prometNaTeret;
    }

    public void setPrometNaTeret(Float prometNaTeret) {
        this.prometNaTeret = prometNaTeret;
    }

    public Float getNovoStanje() {
        return novoStanje;
    }

    public void setNovoStanje(Float novoStanje) {
        this.novoStanje = novoStanje;
    }

    public Racun getZaRacun() {
        return zaRacun;
    }

    public void setZaRacun(Racun zaRacun) {
        this.zaRacun = zaRacun;
    }

    public List<AnalitikaIzvoda> getIzvodi() {
        return izvodi;
    }

    public void setIzvodi(List<AnalitikaIzvoda> izvodi) {
        this.izvodi = izvodi;
    }
}
