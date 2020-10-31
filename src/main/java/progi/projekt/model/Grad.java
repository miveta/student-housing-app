package progi.projekt.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Grad {
    @Id
    @Column(name = "id_grad")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naziv;

    @OneToMany(mappedBy = "grad")
    private List<Dom> domovi;

    //private StudentskiCentar studentskiCentar;
}
