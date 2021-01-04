package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LajkId implements Serializable {

    //@Column(name = "id_student")
    //private Student studentId;

    //@Column(name = "id_oglas")
    //private Oglas oglasId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oglas")
    private Oglas oglas;

    public LajkId() {
    }

    public LajkId(Student student, Oglas oglas) {
        this.student = student;
        this.oglas = oglas;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LajkId lajkId = (LajkId) o;
        return Objects.equals(student, lajkId.student) && Objects.equals(oglas, lajkId.oglas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, oglas);
    }

    /*public LajkId(Student studentId, Oglas oglasId) {
        this.studentId = studentId;
        this.oglasId = oglasId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LajkId lajkId = (LajkId) o;
        return Objects.equals(studentId, lajkId.studentId) && Objects.equals(oglasId, lajkId.oglasId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, oglasId);
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public Oglas getOglasId() {
        return oglasId;
    }

    public void setOglasId(Oglas oglasId) {
        this.oglasId = oglasId;
    }

     */
}
