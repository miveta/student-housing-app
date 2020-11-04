package progi.projekt.model;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.OznakeKategorijaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

//TODO: CHECK
@Entity
public class Soba implements Serializable {
    @Id
    private int broj;

    @Id
    private int kat;

    @Id
    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name="id_paviljon"),
            @JoinColumn(name="id_dom")
    })
    private Paviljon paviljon;

    @Column(name = "broj_kreveta")
    @Enumerated(EnumType.STRING)
    private BrojKrevetaEnum brojKreveta;

    @Column(name = "tip_kupaonice")
    @Enumerated(EnumType.STRING)
    private TipKupaoniceEnum tipKupaonice;

    @Column(name="kategorija")
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

    public Soba(){}

    //Ništa ne smije biti null!
    public Soba(int brojSobe, int kat, Paviljon paviljon, BrojKrevetaEnum brojKreveta, TipKupaoniceEnum tipKupaonice, OznakeKategorijaEnum kategorija){
        if(paviljon != null && brojKreveta != null && tipKupaonice != null && kategorija != null){
            this.paviljon = paviljon;
            this.brojKreveta = brojKreveta;
            this.tipKupaonice = tipKupaonice;
            this.tipKupaonice = tipKupaonice;
            this.broj = brojSobe;
            this.kat = kat;
        } else {
            System.err.println("Ništa u kreaciji sobe ne smije biti null!");
        }
    }
}
