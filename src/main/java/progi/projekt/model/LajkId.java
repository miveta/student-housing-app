package progi.projekt.model;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class LajkId implements Serializable {

    @Column(name = "id_student")
    private Student studentId;

    @Column(name = "id_oglas")
    private Oglas oglasId;

    public LajkId() {
    }

    public LajkId(Student studentId, Oglas oglasId) {
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
}
