package progi.projekt.model;

import progi.projekt.model.enums.BrojKreveta;
import progi.projekt.model.enums.TipKupaonice;

import javax.persistence.*;
import java.util.UUID;

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
    private BrojKreveta brojKreveta;

    @Column(name = "tip_kupaonice")
    @Enumerated(EnumType.STRING)
    private TipKupaonice tipKupaonice;
}
