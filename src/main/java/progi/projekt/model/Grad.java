package progi.projekt.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Grad {
    @Id
    @Column(name = "id_grad")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String naziv;

    @OneToMany(mappedBy = "grad", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Dom> domovi;

    @OneToMany(mappedBy = "grad", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TrazeniUvjeti> trazeniUvjeti;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sc")
    private StudentskiCentar studentskiCentar;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "grad")
    private List<Student> studenti;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<Dom> getDomovi() {
        return domovi;
    }

    public void setDomovi(Set<Dom> domovi) {
        this.domovi = domovi;
    }

    public StudentskiCentar getStudentskiCentar() {
        return studentskiCentar;
    }

    public void setStudentskiCentar(StudentskiCentar studentskiCentar) {
        this.studentskiCentar = studentskiCentar;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grad grad = (Grad) o;
        return Objects.equals(id, grad.id) &&
                Objects.equals(naziv, grad.naziv) &&
                Objects.equals(domovi, grad.domovi) &&
                Objects.equals(studentskiCentar, grad.studentskiCentar) &&
                Objects.equals(studenti, grad.studenti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naziv, domovi, studentskiCentar, studenti);
    }

    @Override
    public String toString() {
        return "Grad{" +
                "id=" + id +
                ", naziv='" + naziv +
                '}';
    }
}
