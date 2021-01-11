package progi.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"grad", "paviljoni"}) // dodano da se izbjegne rekuzija za fetch
public class Dom implements Serializable {
    @Id
    @Column(name = "id_dom")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private boolean imaMenzu;

    @Column(nullable = false)
    private String naziv;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_grad")
    private Grad grad;

    @ManyToMany(mappedBy = "domovi")
    private Set<TrazeniUvjeti> trazeni_uvjeti;

    @OneToMany(mappedBy = "dom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Paviljon> paviljoni;

    public Dom() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isImaMenzu() {
        return imaMenzu;
    }

    public void setImaMenzu(boolean imaMenzu) {
        this.imaMenzu = imaMenzu;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public Set<TrazeniUvjeti> getTrazeni_uvjeti() {
        return trazeni_uvjeti;
    }

    public void setTrazeni_uvjeti(Set<TrazeniUvjeti> trazeni_uvjeti) {
        this.trazeni_uvjeti = trazeni_uvjeti;
    }

    public Set<Paviljon> getPaviljoni() {
        return paviljoni;
    }

    public void setPaviljoni(Set<Paviljon> paviljoni) {
        this.paviljoni = paviljoni;
    }

    @Override
    public String toString() {
        return "Dom{" +
                "id=" + id +
                ", imaMenzu=" + imaMenzu +
                ", naziv='" + naziv + '\'' +
                ", grad=" + grad +
                '}';
    }
}
