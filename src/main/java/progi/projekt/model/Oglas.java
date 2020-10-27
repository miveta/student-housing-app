package progi.projekt.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Oglas {
    @Id
    @Column(name = "id_oglas")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String naslov;
    private String opis;

    private Date objavljen;
}
