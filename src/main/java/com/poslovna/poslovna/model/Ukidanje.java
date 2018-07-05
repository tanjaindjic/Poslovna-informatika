package com.poslovna.poslovna.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Ukidanje {

    @Id
    private long id;

    private Date datumUkidanja;

    @ManyToOne
    private Racun racun;

    @ManyToOne
    private Racun prenosNaRacun;
}
