package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Paviljon implements Serializable {
    @Id
    @Column(name = "id_paviljon")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naziv;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dom")
    private Dom dom;


    @ManyToMany(mappedBy = "paviljoni")
    private Set<TrazeniUvjeti> trazeni_uvjeti;

    @OneToMany(mappedBy = "paviljon", cascade = CascadeType.ALL)
    private Set<Soba> sobe;

    public Paviljon() {
    }

    //Naziv i dom ne smiju biti null!
    public Paviljon(String naziv, Dom dom) {
        if (naziv != null) {
            if (dom != null) {
                this.naziv = naziv;
                this.dom = dom;
            } else {
                System.err.println("Dom paviljona ne smije biti null!");
            }
        } else {
            System.err.println("Naziv paviljona ne smije biti null!");
        }
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Paviljon paviljon = (Paviljon) o;
//        return id.equals(paviljon.id) &&
//                dom.equals(paviljon.dom);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dom);
    }

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

    public Dom getDom() {
        return dom;
    }

    public void setDom(Dom dom) {
        this.dom = dom;
    }

    public Set<TrazeniUvjeti> getTrazeni_uvjeti() {
        return trazeni_uvjeti;
    }

    public void setTrazeni_uvjeti(Set<TrazeniUvjeti> trazeni_uvjeti) {
        this.trazeni_uvjeti = trazeni_uvjeti;
    }

    public Set<Soba> getSobe() {
        return sobe;
    }

    public void setSobe(Set<Soba> sobe) {
        this.sobe = sobe;
    }

    public void addSoba(Soba soba) {
        this.sobe.add(soba);
    }
}
