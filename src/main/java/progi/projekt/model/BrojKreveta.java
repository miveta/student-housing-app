package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class BrojKreveta implements Serializable {
    @Id
    @Column(name = "id_broj_kreveta")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_trazeni_uvjeti")
    private TrazeniUvjeti trazeni_uvjeti;

    @Column(name = "naziv")
    private String naziv;

    public BrojKreveta() {}

    public BrojKreveta(String naziv) { this.naziv = naziv; }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TrazeniUvjeti getTrazeni_uvjeti() {
        return trazeni_uvjeti;
    }

    public void setTrazeni_uvjeti(TrazeniUvjeti trazeni_uvjeti) {
        this.trazeni_uvjeti = trazeni_uvjeti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
