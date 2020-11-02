package progi.projekt.model;

import progi.projekt.model.enums.StatusOglasaEnum;

import javax.persistence.*;
import java.util.UUID;

//TODO: CHECK
@Entity
public class StatusOglasa {
    @Id
    @Column(name = "id_status_oglasa")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private StatusOglasaEnum status;

    @OneToOne
    @JoinColumn(name = "id_student")
    private Student potvrdioOglas;

    @OneToOne
    @JoinColumn(name = "id_oglas")
    private Oglas oglas;

    //Status ne smije biti null!
    public StatusOglasa(StatusOglasaEnum status){
        if(status != null)
            this.status = status;
        else
            System.err.println("Status oglasa ne smije biti null!");
    }

    public StatusOglasaEnum getStatus() {
        return status;
    }

    public void setStatus(StatusOglasaEnum status) {
        this.status = status;
    }

    public Student getPotvrdioOglas() {
        return potvrdioOglas;
    }

    public void setPotvrdioOglas(Student potvrdioOglas) {
        this.potvrdioOglas = potvrdioOglas;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }
}
