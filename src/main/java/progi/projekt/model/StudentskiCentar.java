package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

//TODO: FINISHED
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

    //Naziv i grad ne smiju biti null!
    public StudentskiCentar(String naziv, Grad grad){
        if(naziv != null){
            if(grad != null){
                this.naziv = naziv;
                this.grad = grad;
            }
            else
                System.err.println("Grad studentskog centra ne smije biti null!");
        }
        else
            System.err.println("Naziv studentskog centra ne smije biti null!");
    }

    public StudentskiCentar(){ }
}
