package progi.projekt.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
public class Oglas {
    @Id
    @Column(name = "id_oglas")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naslov;
    private String opis;

    private Date objavljen;
}
