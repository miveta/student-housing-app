package progi.projekt.model;

import javax.persistence.*;

@Entity
public class Kategorija {
    @Id
    @Column(name = "id_kategorija")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ozn_kategorije")
    private String oznakaKategorije;
}
