package progi.projekt.model.mozdaNetreba;

import progi.projekt.model.enums.OznakeKategorijaEnum;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Kategorija {
    @Id
    @Column(name = "id_kategorija")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "ozn_kategorije", nullable = false)
    private OznakeKategorijaEnum oznakaKategorije;

    //Oznaka kategorije ne smije biti null!
    public Kategorija(OznakeKategorijaEnum oznKat){
        if(oznKat != null)
            this.oznakaKategorije = oznKat;
        else
            System.err.println("Oznaka kategorije pri kreaciji nove kategorije ne smije biti null!");
    }
}
