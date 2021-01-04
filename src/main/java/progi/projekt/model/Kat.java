package progi.projekt.model;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class Kat {
    @Id
    @Column(name = "id_dom")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int broj;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_paviljon")
    private Paviljon paviljon;


    @OneToMany(mappedBy = "kat", cascade = CascadeType.ALL)
    private Set<Soba> sobe;

    public Kat() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public Paviljon getPaviljon() {
        return paviljon;
    }

    public void setPaviljon(Paviljon paviljon) {
        this.paviljon = paviljon;
    }

    public Set<Soba> getSobe() {
        return sobe;
    }

    public void setSobe(Set<Soba> sobe) {
        this.sobe = sobe;
    }
}
