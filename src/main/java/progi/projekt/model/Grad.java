package progi.projekt.model;

import javax.persistence.*;
import java.util.List;

public class Grad {
    @Id
    @Column(name = "id_grad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String naziv;

    @OneToMany(mappedBy = "grad")
    private List<Dom> domovi;

    private StudentskiCentar studentskiCentar;
}
