package progi.projekt.model.mozdaNetreba;

import progi.projekt.model.Soba;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.trazeniUvjeti;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class BrojKreveta {
    @Id
    @Column(name = "id_broj_kreveta")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private BrojKrevetaEnum broj;

    @OneToMany(mappedBy = "brojKreveta")
    private Set<progi.projekt.model.trazeniUvjeti> trazeniUvjeti;

    @OneToMany(mappedBy = "brojKreveta")
    private Set<Soba> sobe;

    //Broj kreveta ne smije biti null!
    public BrojKreveta(BrojKrevetaEnum broj){
        if(broj != null)
            this.broj = broj;
        else
            System.err.println("Broj u broju kreveta ne smije biti null!");
    }

    public void addToTrazeniUvjeti(trazeniUvjeti tu){
        trazeniUvjeti.add(tu);
    }

    public Set<progi.projekt.model.trazeniUvjeti> getTrazeniUvjeti() {
        return trazeniUvjeti;
    }

    public void setTrazeniUvjeti(Set<progi.projekt.model.trazeniUvjeti> trazeniUvjeti) {
        this.trazeniUvjeti = trazeniUvjeti;
    }

    public BrojKrevetaEnum getBroj() {
        return broj;
    }

    public void setBroj(BrojKrevetaEnum broj) {
        this.broj = broj;
    }
}
