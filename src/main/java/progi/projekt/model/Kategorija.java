package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Kategorija {
    @Id
    @Column(name = "id_kategorija")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "ozn_kategorije")
    private String oznakaKategorije;
}
