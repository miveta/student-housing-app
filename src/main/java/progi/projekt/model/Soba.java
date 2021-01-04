package progi.projekt.model;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.OznakeKategorijaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Soba implements Serializable {
    @Id
    @Column(name = "id_soba")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_kat")
    private Kat kat;

    private int broj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_oglas")
    private Oglas oglas;

    @Column(nullable = true, name = "id_soba")
    private Integer idSoba;

    @Column(name = "broj_kreveta")
    @Enumerated(EnumType.STRING)
    private BrojKrevetaEnum brojKreveta;

    @Column(name = "tip_kupaonice")
    @Enumerated(EnumType.STRING)
    private TipKupaoniceEnum tipKupaonice;

    @Column(name = "kategorija")
    @Enumerated(EnumType.STRING)
    private OznakeKategorijaEnum kategorija;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Soba soba = (Soba) o;
        return kat == soba.kat &&
                broj == soba.broj &&
                paviljon.equals(soba.paviljon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(broj, kat, paviljon);
    }

    public Soba() {
    }

    //Ništa ne smije biti null!
    public Soba(int brojSobe, Kat kat, Paviljon paviljon, BrojKrevetaEnum brojKreveta, TipKupaoniceEnum tipKupaonice, OznakeKategorijaEnum kategorija) {
        if (brojKreveta != null && tipKupaonice != null && kategorija != null) {
            this.brojKreveta = brojKreveta;
            this.tipKupaonice = tipKupaonice;
            this.broj = brojSobe;
            //this.kat = kat;
        } else {
            System.err.println("Ništa u kreaciji sobe ne smije biti null!");
        }
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public BrojKrevetaEnum getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(BrojKrevetaEnum brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public TipKupaoniceEnum getTipKupaonice() {
        return tipKupaonice;
    }

    public void setTipKupaonice(TipKupaoniceEnum tipKupaonice) {
        this.tipKupaonice = tipKupaonice;
    }

    public OznakeKategorijaEnum getKategorija() {
        return kategorija;
    }

    public void setKategorija(OznakeKategorijaEnum kategorija) {
        this.kategorija = kategorija;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }

    public Kat getKat() {
        return kat;
    }

    public void setKat(Kat kat) {
        this.kat = kat;
    }
}

