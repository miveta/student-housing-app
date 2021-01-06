package progi.projekt.model;

import progi.projekt.model.enums.StatusOglasaEnum;

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

    @Column(nullable = false)
    private Date objavljen;


    private StatusOglasaEnum statusOglasa;

    @OneToMany(mappedBy = "oglas", cascade = CascadeType.ALL)
    private List<Obavijest> obavijesti;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student student;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kandOglas")
    private List<Kandidat> kandidati;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_soba")
    private Soba soba;

    @OneToOne
    @JoinColumn(name = "id_trazeni_uvjeti")
    private TrazeniUvjeti trazeniUvjeti;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public Date getObjavljen() {
        return objavljen;
    }

    public void setObjavljen(Date objavljen) {
        this.objavljen = objavljen;
    }

    public StatusOglasaEnum getStatusOglasa() {
        return statusOglasa;
    }

    public void setStatusOglasa(StatusOglasaEnum statusOglasa) {
        this.statusOglasa = statusOglasa;
    }

    public List<Obavijest> getObavijesti() {
        return obavijesti;
    }

    public void setObavijesti(List<Obavijest> obavijesti) {
        this.obavijesti = obavijesti;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

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

    public TrazeniUvjeti getTrazeniUvjeti() {
        return trazeniUvjeti;
    }

    public void setTrazeniUvjeti(TrazeniUvjeti trazeniUvjeti) {
        this.trazeniUvjeti = trazeniUvjeti;
    }
}
