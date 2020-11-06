package progi.projekt.model;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.OznakeKategorijaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class TrazeniUvjeti {
    @Id
    @Column(name = "id_trazeni_uvjeti")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "broj_kreveta", nullable = false)
    @Enumerated(EnumType.STRING)
    private BrojKrevetaEnum brojKreveta;

    @Column(name = "tip_kupaonice", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipKupaoniceEnum tipKupaonice;

    @Column(name = "kategorija", nullable = false)
    @Enumerated(EnumType.STRING)
    private OznakeKategorijaEnum kategorija;

    private String komentar;
    @Column(nullable = false)
    private int godina;

    @OneToOne
    @JoinColumn(name = "id_student")
    private Student traziStudent;

    public TrazeniUvjeti() {
    }

    //Ništa ne smije biti null osim komentara
    public TrazeniUvjeti(BrojKrevetaEnum brojKreveta, TipKupaoniceEnum tipKupaonice, OznakeKategorijaEnum kategorija, int godina, Student student, String komentar) {
        if (brojKreveta != null && tipKupaonice != null && kategorija != null && student != null) {
            this.brojKreveta = brojKreveta;
            this.tipKupaonice = tipKupaonice;
            this.kategorija = kategorija;
            this.godina = godina;
            this.traziStudent = student;
            this.komentar = komentar;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BrojKrevetaEnum getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(BrojKrevetaEnum brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public TipKupaoniceEnum getTipKupaonice() {
        return tipKupaonice;
    }

    public void setTipKupaonice(TipKupaoniceEnum tipKupaonice) {
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
}
