package progi.projekt.model;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.OznakeKategorijaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Soba implements Serializable {
    @Id
    private int broj;


    private int kat;

    @ManyToOne
    @JoinColumn(name = "id_paviljon")
    private Paviljon paviljon;

    @JoinColumn(name = "id_broj_kreveta")
    private BrojKreveta brojKreveta;

    @JoinColumn(name = "id_tip_kupaonice")
    private TipKupaonice tipKupaonice;

    @Column(name = "kategorija")
    @Enumerated(EnumType.STRING)
    private OznakeKategorijaEnum kategorija;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Soba soba = (Soba) o;
        return kat == soba.kat &&
                broj == soba.broj
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(broj, kat);
    }

    public Soba() {
    }

    //Ništa ne smije biti null!
    public Soba(int brojSobe, int kat,  BrojKreveta brojKreveta, TipKupaonice tipKupaonice, OznakeKategorijaEnum kategorija) {
        if (brojKreveta != null && tipKupaonice != null && kategorija != null) {

            this.brojKreveta = brojKreveta;
            this.tipKupaonice = tipKupaonice;
            this.broj = brojSobe;
            this.kat = kat;
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

    public int getKat() {
        return kat;
    }

    public void setKat(int kat) {
        this.kat = kat;
    }

    public BrojKreveta getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(BrojKreveta brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public TipKupaonice getTipKupaonice() {
        return tipKupaonice;
    }

    public void setTipKupaonice(TipKupaonice tipKupaonice) {
        this.tipKupaonice = tipKupaonice;
    }

    public OznakeKategorijaEnum getKategorija() {
        return kategorija;
    }

    public void setKategorija(OznakeKategorijaEnum kategorija) {
        this.kategorija = kategorija;
    }

    public Paviljon getPaviljon() {

        return paviljon;
    }

    public void setPaviljon(Paviljon paviljon) {
        this.paviljon = paviljon;
    }
}
