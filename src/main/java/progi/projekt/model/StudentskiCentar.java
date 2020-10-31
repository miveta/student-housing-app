package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class StudentskiCentar {
    @Id
    @Column(name = "id_sc")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naziv;


}
