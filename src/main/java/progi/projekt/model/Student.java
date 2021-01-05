package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Student implements Serializable, Korisnik {
    @Id
    @Column(name = "id_korisnik")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private String jmbag;

    // Korisnicko ime ne smije biti null, te mora biti unique
    @Column(nullable = false, unique = true, name = "korisnicko_ime")
    private String korisnickoIme;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "hash_lozinke")
    private String lozinka;

    @Column(nullable = false, name = "obavijesti_na_mail")
    private boolean obavijestiNaMail;

    @ManyToMany(targetEntity = Obavijest.class, cascade = CascadeType.ALL)
    private List<Obavijest> obavijesti;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_status_oglasa")
    private StatusOglasa potvrdioOglas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_trazeni_uvjeti")
    private TrazeniUvjeti uvjeti;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="oglas")
    private Oglas oglas;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="id_grad")
    private Grad grad;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_soba")
    private Soba soba;


    @Override
    public String getTipKorisnika() {
        return "student";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public boolean isObavijestiNaMail() {
        return obavijestiNaMail;
    }

    public void setObavijestiNaMail(boolean obavijestiNaMail) {
        this.obavijestiNaMail = obavijestiNaMail;
    }

    public List<Obavijest> getObavijesti() {
        return obavijesti;
    }

    public void setObavijesti(List<Obavijest> obavijesti) {
        this.obavijesti = obavijesti;
    }

    public StatusOglasa getPotvrdioOglas() {
        return potvrdioOglas;
    }

    public void setPotvrdioOglas(StatusOglasa potvrdioOglas) {
        this.potvrdioOglas = potvrdioOglas;
    }

    public TrazeniUvjeti getUvjeti() {
        return uvjeti;
    }

    public void setUvjeti(TrazeniUvjeti uvjeti) {
        this.uvjeti = uvjeti;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public Soba getSoba() {
        return soba;
    }

    public void setSoba(Soba soba) {
        this.soba = soba;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return obavijestiNaMail == student.obavijestiNaMail &&
                Objects.equals(id, student.id) &&
                Objects.equals(jmbag, student.jmbag) &&
                Objects.equals(korisnickoIme, student.korisnickoIme) &&
                Objects.equals(ime, student.ime) &&
                Objects.equals(prezime, student.prezime) &&
                Objects.equals(email, student.email) &&
                Objects.equals(lozinka, student.lozinka) &&
                Objects.equals(obavijesti, student.obavijesti) &&
                Objects.equals(potvrdioOglas, student.potvrdioOglas) &&
                Objects.equals(uvjeti, student.uvjeti) &&
                Objects.equals(oglas, student.oglas) &&
                Objects.equals(grad, student.grad) &&
                Objects.equals(soba, student.soba);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jmbag, korisnickoIme, ime, prezime, email, lozinka, obavijestiNaMail, obavijesti, potvrdioOglas, uvjeti, oglas, grad, soba);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", jmbag='" + jmbag + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", email='" + email + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", obavijestiNaMail=" + obavijestiNaMail +
                ", obavijesti=" + obavijesti +
                ", potvrdioOglas=" + potvrdioOglas +
                ", uvjeti=" + uvjeti +
                ", oglas=" + oglas +
                ", grad=" + grad +
                ", soba=" + soba +
                '}';
    }
}
