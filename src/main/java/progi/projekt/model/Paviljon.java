package progi.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import progi.projekt.model.enums.OznakeKategorijaEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"dom"}) // dodano da se izbjegne rekuzija za fetch
public class Paviljon implements Serializable {
    @Id
    @Column(name = "id_paviljon")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naziv;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dom")
    private Dom dom;

    @OneToMany(mappedBy = "paviljon", cascade = CascadeType.ALL)
    private Set<Kat> katovi;

    @Column(name = "kategorija")
    @Enumerated(EnumType.STRING)
    private OznakeKategorijaEnum kategorija;

    public Paviljon() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paviljon paviljon = (Paviljon) o;
        return id.equals(paviljon.id) &&
                dom.equals(paviljon.dom);
    }

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

    public Set<Kat> getKatovi() {
        return katovi;
    }

    public void setKatovi(Set<Kat> katovi) {
        this.katovi = katovi;
    }

    public OznakeKategorijaEnum getKategorija() {
        return kategorija;
    }

    public void setKategorija(OznakeKategorijaEnum kategorija) {
        this.kategorija = kategorija;
    }
}
