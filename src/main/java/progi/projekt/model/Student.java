package progi.projekt.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import progi.projekt.model.enums.StatusOglasaEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    @ManyToMany(targetEntity = Obavijest.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Obavijest> obavijesti;

    @OneToMany(mappedBy = "student")
    private Set<Oglas> oglasi;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_grad")
    private Grad grad;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_soba")
    private Soba soba;


    public Oglas getAktivniOglas() {
        Set<Oglas> oglasi = getOglasi();

        if (oglasi == null) return null;

        oglasi = oglasi.stream().filter(o -> o.getStatusOglasa().equals(StatusOglasaEnum.AKTIVAN)).collect(Collectors.toSet());
        if (oglasi.isEmpty()) return null;

        if (oglasi.size() > 1) throw new IllegalArgumentException("user smije imat samo jedan aktivan oglas");

        return oglasi.iterator().next();
    }

    public void setAktivniOglas(Oglas noviOglas) {
        if (!noviOglas.getStatusOglasa().equals(StatusOglasaEnum.AKTIVAN))
            throw new IllegalArgumentException("aktivni oglas mora bit aktivan");
        Oglas oglas = getAktivniOglas();

        if (oglas != null && !noviOglas.getId().equals(oglas.getId()))
            throw new IllegalArgumentException("user smije imat samo jedan aktivan oglas");

        if (oglasi == null) oglasi = new HashSet<>();
        oglasi.add(noviOglas);
    }

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

    @Override
    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    @Override
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    @Override
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
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

    @Override
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

    public Set<Oglas> getOglasi() {
        return oglasi;
    }

    public void setOglasi(Set<Oglas> oglasi) {
        this.oglasi = oglasi;
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
        return Objects.equals(jmbag, student.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
