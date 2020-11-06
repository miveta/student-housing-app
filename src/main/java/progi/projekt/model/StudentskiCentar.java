package progi.projekt.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class StudentskiCentar {
    @Id
    @Column(name = "id_sc")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naziv;

    @OneToOne
    @JoinColumn(name = "id_grad", nullable = false)
    private Grad grad;

    @OneToMany(mappedBy = "zaposlenSC")
    List<ZaposlenikSC> zaposlenici;

    //Naziv i grad ne smiju biti null!
    public StudentskiCentar(String naziv, Grad grad) {
        if (naziv != null) {
            if (grad != null) {
                this.naziv = naziv;
                this.grad = grad;
            } else
                System.err.println("Grad studentskog centra ne smije biti null!");
        } else
            System.err.println("Naziv studentskog centra ne smije biti null!");
    }

    public StudentskiCentar() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public List<ZaposlenikSC> getZaposlenici() {
        return zaposlenici;
    }

    public void setZaposlenici(List<ZaposlenikSC> zaposlenici) {
        this.zaposlenici = zaposlenici;
    }
}
