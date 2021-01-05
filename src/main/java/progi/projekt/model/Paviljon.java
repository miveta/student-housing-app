package progi.projekt.model;

import progi.projekt.model.enums.OznakeKategorijaEnum;

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

    private int brojKatova;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dom")
    private Dom dom;

    @OneToMany(mappedBy = "paviljon", cascade = CascadeType.ALL)
    private Set<Soba> sobe;

    @Column(name = "kategorija")
    @Enumerated(EnumType.STRING)
    private OznakeKategorijaEnum kategorija;

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

    public int getBrojKatova() {
        return brojKatova;
    }

    public void setBrojKatova(int brojKatova) {
        this.brojKatova = brojKatova;
    }

    public Dom getDom() {
        return dom;
    }

    public void setDom(Dom dom) {
        this.dom = dom;
    }

    public Set<Soba> getSobe() {
        return sobe;
    }

    public void setSobe(Set<Soba> sobe) {
        this.sobe = sobe;
    }

    public OznakeKategorijaEnum getKategorija() {
        return kategorija;
    }

    public void setKategorija(OznakeKategorijaEnum kategorija) {
        this.kategorija = kategorija;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paviljon paviljon = (Paviljon) o;
        return brojKatova == paviljon.brojKatova &&
                Objects.equals(id, paviljon.id) &&
                Objects.equals(naziv, paviljon.naziv) &&
                Objects.equals(dom, paviljon.dom) &&
                Objects.equals(sobe, paviljon.sobe) &&
                kategorija == paviljon.kategorija;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naziv, brojKatova, dom, sobe, kategorija);
    }

    @Override
    public String toString() {
        return "Paviljon{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", brojKatova=" + brojKatova +
                ", dom=" + dom +
                ", sobe=" + sobe +
                ", kategorija=" + kategorija +
                '}';
    }
}
