package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

//TODO : WEAK ENTITY
@Entity
public class Paviljon {
    @Id
    @Column(name = "id_paviljon")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naziv;
}
