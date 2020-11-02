package progi.projekt.model;

import progi.projekt.model.mozdaNetreba.BrojKreveta;

import javax.persistence.*;
import java.util.UUID;

//TODO: ALL
@Entity
public class trazeniUvjeti {
    @Id
    @Column(name = "id_trazeni_uvjeti")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_broj_kreveta")
    private BrojKreveta brojKreveta;
}
