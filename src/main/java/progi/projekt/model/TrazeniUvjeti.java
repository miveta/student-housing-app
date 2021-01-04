package progi.projekt.model;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.OznakeKategorijaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
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

    @ManyToMany
    @JoinTable(
            name = "kreveti_uvjeti",
            joinColumns = @JoinColumn(name = "id_broj_kreveta"),
            inverseJoinColumns = @JoinColumn(name = "id_trazeni_uvjeti"))
    private Set<BrojKreveta> brojKreveta;

    @ManyToMany
    @JoinTable(
            name = "kupaonice_uvjeti",
            joinColumns = @JoinColumn(name = "id_tip_kupaonice"),
            inverseJoinColumns = @JoinColumn(name = "id_trazeni_uvjeti"))
    private Set<TipKupaonice> tipKupaonice;

    @Column(name = "kategorija")
    @Enumerated(EnumType.STRING)
    private OznakeKategorijaEnum kategorija;

    private String komentar;
    @Column(name = "godina")
    private int godina;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student traziStudent;

    public TrazeniUvjeti() {
    }

    //Ništa ne smije biti null osim komentara


    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<BrojKreveta> getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(Set<BrojKreveta> brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public Set<TipKupaonice> getTipKupaonice() {
        return tipKupaonice;
    }

    public void setTipKupaonice(Set<TipKupaonice> tipKupaonice) {
        this.tipKupaonice = tipKupaonice;
    }

    public OznakeKategorijaEnum getKategorija() {
        return kategorija;
    }

    public void setKategorija(OznakeKategorijaEnum kategorija) {
        this.kategorija = kategorija;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
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

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public Student getTraziStudent() {
        return traziStudent;
    }

    public void setTraziStudent(Student traziStudent) {
        this.traziStudent = traziStudent;
    }

    public Set<Integer> getKatovi() {
        return katovi;
    }

    public void setKatovi(Set<Integer> katovi) {
        this.katovi = katovi;
    }
}
