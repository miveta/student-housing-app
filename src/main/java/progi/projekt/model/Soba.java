package progi.projekt.model;

import lombok.Data;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
public class Soba implements Serializable {
    @Id
    @Column(name = "id_soba")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_paviljon")
    private Paviljon paviljon;

    private int kat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oglas")
    private Oglas oglas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student student;

    @Column(name = "broj_kreveta")
    @Enumerated(EnumType.STRING)
    private BrojKrevetaEnum brojKreveta;

    @Column(name = "tip_kupaonice")
    @Enumerated(EnumType.STRING)
    private TipKupaoniceEnum tipKupaonice;

    @Lob
    private String komentar;
}

