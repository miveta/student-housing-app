package progi.projekt.model;

import javax.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy = "grad", cascade = CascadeType.ALL)
    private Set<Dom> domovi;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sc")
    private StudentskiCentar studentskiCentar;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "grad")
    private List<Student> studenti;

    public Grad() {
    }

    public void addDom(Dom dom) {
        this.domovi.add(dom);
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }
}
