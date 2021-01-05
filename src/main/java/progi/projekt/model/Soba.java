package progi.projekt.model;

import org.hibernate.annotations.Type;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Soba implements Serializable {
    @Id
    @Column(name = "id_soba")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_paviljon")
    private Paviljon paviljon;

    private int kat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oglas")
    private Oglas oglas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student student;

    @Column(name = "broj_kreveta")
    @Enumerated(EnumType.STRING)
    private BrojKrevetaEnum brojKreveta;

    @Column(name = "tip_kupaonice")
    @Enumerated(EnumType.STRING)
    private TipKupaoniceEnum tipKupaonice;

    @Lob
    @Type(type = "text")
    private String komentar;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Paviljon getPaviljon() {
        return paviljon;
    }

    public void setPaviljon(Paviljon paviljon) {
        this.paviljon = paviljon;
    }

    public int getKat() {
        return kat;
    }

    public void setKat(int kat) {
        this.kat = kat;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Soba soba = (Soba) o;
        return
                Objects.equals(id, soba.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Soba{" +
                "id=" + id +
                ", paviljon=" + paviljon +
                ", kat=" + kat +
                ", brojKreveta=" + brojKreveta +
                ", tipKupaonice=" + tipKupaonice +
                ", komentar='" + komentar + '\'' +
                '}';
    }
}

