package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Dom {
    @Id
    @Column(name = "id_dom")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private boolean imaMenzu;
    private String naziv;

    @ManyToOne
    @JoinColumn(name = "id_grad")
    private Grad grad;
}
