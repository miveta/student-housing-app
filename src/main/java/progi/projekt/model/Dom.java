package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
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

    //Naziv i grad ne smiju biti null!
    public Dom(String naziv, Grad grad, boolean imaMenzu) {
        if (naziv != null) {
            if (grad != null) {
                this.naziv = naziv;
                this.grad = grad;
                this.imaMenzu = imaMenzu;
            } else
                System.err.print("Grad u konstruktoru doma ne smije biti null!");
        } else
            System.err.print("Naziv doma ne smije biti null!");
    }

    public Dom() {
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

    public UUID getId() {
        return id;
    }

    public Set<TrazeniUvjeti> getTrazeni_uvjeti() {
        return trazeni_uvjeti;
    }

    public void setTrazeni_uvjeti(Set<TrazeniUvjeti> trazeni_uvjeti) {
        this.trazeni_uvjeti = trazeni_uvjeti;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Paviljon> getPaviljoni() {
        return paviljoni;
    }

    public void setPaviljoni(Set<Paviljon> paviljoni) {
        this.paviljoni = paviljoni;
    }
}
