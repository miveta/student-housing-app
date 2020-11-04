package progi.projekt.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

//TODO: INHERITANCE?????
@Entity
public class Student{
    @Id
    @Column(nullable = false, length = 10)
    private String jmbag;

    @ManyToMany(targetEntity = Obavijest.class)
    protected List<Obavijest> obavijesti;

    @OneToOne
    @JoinColumn(name="id_status_oglasa")
    private Oglas potvrdioOglas;

    @OneToOne
    @JoinColumn(name = "id_trazeni_uvjeti")
    private TrazeniUvjeti uvjeti;

    public List<Obavijest> getObavijesti() {
        return obavijesti;
    }

    public void setObavijesti(List<Obavijest> obavijesti) {
        this.obavijesti = obavijesti;
    }
}
