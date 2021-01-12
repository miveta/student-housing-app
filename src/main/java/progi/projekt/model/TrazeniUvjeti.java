package progi.projekt.model;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.OznakeKategorijaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class TrazeniUvjeti {
    @Id
    @Column(name = "id_trazeni_uvjeti")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ElementCollection
    private Set<Integer> katovi;

    @ManyToOne
    @JoinColumn(name = "id_grad")
    private Grad grad;

    @ManyToMany
    @JoinTable(
            name = "domovi_uvjeti",
            joinColumns = @JoinColumn(name = "id_dom"),
            inverseJoinColumns = @JoinColumn(name = "id_trazeni_uvjeti"))
    private Set<Dom> domovi;

    @ManyToMany
    @JoinTable(
            name = "paviljoni_uvjeti",
            joinColumns = @JoinColumn(name = "id_paviljon"),
            inverseJoinColumns = @JoinColumn(name = "id_trazeni_uvjeti"))
    private Set<Paviljon> paviljoni;

    @ElementCollection(targetClass = BrojKrevetaEnum.class)
    private Set<BrojKrevetaEnum> brojKreveta;

    @ElementCollection(targetClass = TipKupaoniceEnum.class)
    private Set<TipKupaoniceEnum> tipKupaonice;

    @ElementCollection(targetClass = OznakeKategorijaEnum.class)
    private Set<OznakeKategorijaEnum> kategorija;

    /*@OneToOne
    @JoinColumn(name = "id_oglas")*/
    @OneToOne(mappedBy = "trazeniUvjeti")
    private Oglas oglas;

    public TrazeniUvjeti(){
        this.brojKreveta = new HashSet<>();
        this.katovi = new HashSet<>();
        this.domovi = new HashSet<>();
        this.paviljoni = new HashSet<>();
        this.tipKupaonice = new HashSet<>();
    };
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Integer> getKatovi() {
        return katovi;
    }

    public void setKatovi(Set<Integer> katovi) {
        this.katovi = katovi;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public Set<Dom> getDomovi() {
        return domovi;
    }

    public void setDomovi(Set<Dom> domovi) {
        this.domovi = domovi;
    }

    public Set<Paviljon> getPaviljoni() {
        return paviljoni;
    }

    public void setPaviljoni(Set<Paviljon> paviljoni) {
        this.paviljoni = paviljoni;
    }

    public Set<BrojKrevetaEnum> getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(Set<BrojKrevetaEnum> brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public Set<TipKupaoniceEnum> getTipKupaonice() {
        return tipKupaonice;
    }

    public void setTipKupaonice(Set<TipKupaoniceEnum> tipKupaonice) {
        this.tipKupaonice = tipKupaonice;
    }

    public Set<OznakeKategorijaEnum> getKategorija() {
        return kategorija;
    }

    public void setKategorija(Set<OznakeKategorijaEnum> kategorija) {
        this.kategorija = kategorija;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }

}
