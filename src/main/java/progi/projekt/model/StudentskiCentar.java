package progi.projekt.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class StudentskiCentar {
    @Id
    @Column(name = "id_sc")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String naziv;


}
