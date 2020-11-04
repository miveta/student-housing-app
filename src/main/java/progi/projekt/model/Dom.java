package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

//TODO: FINISHED
@Entity
public class Dom implements Serializable {
    @Id
    @Column(name = "id_dom")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private boolean imaMenzu;

    @Column(nullable = false)
    private String naziv;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_grad")
    private Grad grad;

    @OneToMany(mappedBy = "dom")
    private Set<Paviljon> paviljoni;

    //Naziv i grad ne smiju biti null!
    public Dom(String naziv, Grad grad, boolean imaMenzu){
        if(naziv != null){
            if(grad != null){
                this.naziv = naziv;
                this.grad = grad;
                this.imaMenzu = imaMenzu;
            } else
                System.err.print("Grad u konstruktoru doma ne smije biti null!");
        }
        else
            System.err.print("Naziv doma ne smije biti null!");
    }

    public Dom(){}

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
}
