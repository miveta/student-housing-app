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

    @OneToMany(mappedBy = "grad")
    private Set<Dom> domovi;

    @OneToOne
    @JoinColumn(name = "id_sc", nullable = false)
    private StudentskiCentar studentskiCentar;

    //Naziv i studentskiCentar ne smiju biti null
    public Grad(String naziv, StudentskiCentar studentskiCentar, Set<Dom> domovi){
        if(naziv != null){
            if(studentskiCentar != null){
                this.naziv = naziv;
                this.studentskiCentar = studentskiCentar;
                this.domovi = domovi;
            }
            else{
                System.err.print("SC ne smije biti null pri kreiranju grada!");
            }
        }
        else{
            System.err.println("Naziv grada ne smije biti null!");
        }
    }

    public Grad(){}

    public void addDom(Dom dom){
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
}
