package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
public class TipKupaonice implements Serializable {
    @Id
    @Column(name = "id_tip_kupaonice")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany(mappedBy = "tipKupaonice")
    private Set<TrazeniUvjeti> trazeni_uvjeti;

    @Column(name = "tip")
    private String tip;

    public TipKupaonice() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<TrazeniUvjeti> getTrazeni_uvjeti() {
        return trazeni_uvjeti;
    }

    public void setTrazeni_uvjeti(Set<TrazeniUvjeti> trazeni_uvjeti) {
        this.trazeni_uvjeti = trazeni_uvjeti;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
