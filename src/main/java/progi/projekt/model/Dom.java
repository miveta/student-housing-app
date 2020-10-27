package progi.projekt.model;

import javax.persistence.*;

@Entity
public class Dom {
    @Id
    @Column(name = "id_dom")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean imaMenzu;
    private String naziv;

    @ManyToOne
    @JoinColumn(name = "id_grad")
    private Grad grad;

}
