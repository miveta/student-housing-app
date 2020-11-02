package progi.projekt.model;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;
import progi.projekt.model.mozdaNetreba.BrojKreveta;

import javax.persistence.*;
import java.util.UUID;

//TODO: WEAK ENTITY
@Entity
public class Soba {
    @Id
    @Column(name = "id_soba")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int broj;
    private int kat;

    @Column(name = "broj_kreveta")
    @Enumerated(EnumType.STRING)
    private BrojKrevetaEnum brojKreveta;

    @Column(name = "tip_kupaonice")
    @Enumerated(EnumType.STRING)
    private TipKupaoniceEnum tipKupaonice;
}
