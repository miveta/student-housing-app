package progi.projekt.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Obavijest {
    @Id
    @Column(name = "id_obavijest")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tekst;
    private Student primatelj;
    private boolean procitana;
    private Date vrijeme;
}
