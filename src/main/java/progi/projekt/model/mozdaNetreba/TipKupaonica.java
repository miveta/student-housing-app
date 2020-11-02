package progi.projekt.model.mozdaNetreba;

import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class TipKupaonica {
    @Id
    @Column(name = "id_tip_kupaonica")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private TipKupaoniceEnum tip;
}
