package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Lajk implements Serializable {
    private int ocjena;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_korisnik")
    private Student likedByStudent;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_oglas")
    private Oglas likedOglas;

    public Lajk() {
    }

    //Nista ne smije biti null!
    //Ocjena mora biti 1,2 ili 3
    public Lajk(Student likedBy, Oglas liked, int ocjena) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lajk lajk = (Lajk) o;
        return likedByStudent.equals(lajk.likedByStudent) &&
                likedOglas.equals(lajk.likedOglas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likedByStudent, likedOglas);
    }
}
