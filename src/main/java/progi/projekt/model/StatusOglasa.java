package progi.projekt.model;

import progi.projekt.model.enums.StatusOglasaEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

//TODO: FINISHED
@Entity
public class StatusOglasa implements Serializable {
    @Id
    @Column(name = "id_status_oglasa")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private StatusOglasaEnum status;

    @OneToOne
    @JoinColumn(name = "id_oglas")
    private Oglas oglas;

    @OneToOne
    @JoinColumn(name = "id_student")
    private Student potvrdioOglas;


    //Status i oglas ne smiju biti null!
    public StatusOglasa(StatusOglasaEnum status, Oglas oglas){
        if(status != null)
            if(oglas != null) {
                this.status = status;
                this.oglas = oglas;
            } else{
                System.err.println("Status oglasa se mora odnositi na neki oglas!");
            }
        else
            System.err.println("Status oglasa ne smije biti null!");
    }

    public StatusOglasa(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusOglasa that = (StatusOglasa) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(oglas, that.oglas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oglas);
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
