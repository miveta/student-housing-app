package progi.projekt.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
public class Obavijest {
    @Id
    @Column(name = "id_obavijest")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String tekst;
    //private Student primatelj;
    private boolean procitana;
    private Date vrijeme;
}
