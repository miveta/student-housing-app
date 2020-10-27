package progi.projekt.model;

import javax.persistence.*;

@Entity
public class Paviljon {
    @Id
    @Column(name = "id_paviljon")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String naziv;
}
