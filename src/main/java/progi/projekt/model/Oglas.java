package progi.projekt.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Oglas {
    @Id
    @Column(name = "id_oglas")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String naslov;
    private String opis;
    @Column(nullable = false)
    private int godina;
    private Date objavljen;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_status_oglasa")
    private StatusOglasa status;

    @OneToMany(mappedBy = "oglas", cascade = CascadeType.ALL)
    private List<Obavijest> obavijesti;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_student")
    private Student student;

    /*@OneToMany(mappedBy="oglas",cascade = CascadeType.ALL)
    private List<Lajk> lajkovi;*/

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kandOglas")
    private List<Kandidat> kandidati;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_soba")
    private Soba soba;

    //Naslov i godina ne smiju biti null
    public Oglas(String naslov, String opis, int godina, Date objavljen) {
        if (naslov != null) {
            this.naslov = naslov;
            this.opis = opis;
            this.godina = godina;
            this.objavljen = objavljen;
        } else
            System.err.println("Naslov oglasa ne smije biti null!");
    }

    public Oglas() {
    }

    public void addObavijest(Obavijest obav) {
        obavijesti.add(obav);
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public Date getObjavljen() {
        return objavljen;
    }

    public void setObjavljen(Date objavljen) {
        this.objavljen = objavljen;
    }

    public List<Obavijest> getObavijesti() {
        return obavijesti;
    }

    public StatusOglasa getStatus() {
        return status;
    }

    public void setStatus(StatusOglasa status) {
        this.status = status;
    }

    public void setObavijesti(List<Obavijest> obavijesti) {
        this.obavijesti = obavijesti;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

 /*   public List<Lajk> getLajkovi() {
        return lajkovi;
    }

    public void setLajkovi(List<Lajk> lajkovi) {
        this.lajkovi = lajkovi;
    }*/

    public List<Kandidat> getKandidati() {
        return kandidati;
    }

    public void setKandidati(List<Kandidat> kandidati) {
        this.kandidati = kandidati;
    }

    public Soba getSoba() {
        return soba;
    }

    public void setSoba(Soba soba) {
        this.soba = soba;
    }
}
