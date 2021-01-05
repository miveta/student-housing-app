package progi.projekt.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Grad {
    @Id
    @Column(name = "id_grad")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String naziv;

    @OneToMany(mappedBy = "grad", cascade = CascadeType.ALL)
    private Set<Dom> domovi;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sc")
    private StudentskiCentar studentskiCentar;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "grad")
    private List<Student> studenti;
}
