package progi.projekt.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Lajkovi implements Serializable {
    private int ocjena;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student likedByStudent;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_oglas")
    private Oglas likedOglas;

    public Lajkovi() {
    }

    //Nista ne smije biti null!
    //Ocjena mora biti 1,2 ili 3
    public Lajkovi(Student likedBy, Oglas liked, int ocjena) {
        if (likedBy != null) {
            if (liked != null) {
                if (ocjena == 1 || ocjena == 2 || ocjena == 3) {
                    this.likedByStudent = likedBy;
                    this.likedOglas = liked;
                    this.ocjena = ocjena;
                } else {
                    System.err.println("Ocjena lajka mora biti 1,2 ili 3!");
                }
            } else {
                System.err.println("Lajk mora biti povezan sa oglasom!");
            }
        } else {
            System.err.println("Lajk mora biti povezan sa studentom!");
        }
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    public Student getLikedByStudent() {
        return likedByStudent;
    }

    public void setLikedByStudent(Student likedByStudent) {
        this.likedByStudent = likedByStudent;
    }

    public Oglas getLikedOglas() {
        return likedOglas;
    }

    public void setLikedOglas(Oglas likedOglas) {
        this.likedOglas = likedOglas;
    }
}
