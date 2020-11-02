package progi.projekt.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

//TODO: CHECK
@Entity
public class Korisnik {
    @Id
    @Column(name = "id_korisnik")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Korisnicko ime ne smije biti null, te mora biti unique
    @Column(nullable = false,unique = true, name = "korisnicko_ime")
    private String korisnickoIme;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "hash_lozinke")
    private String lozinka;

    @Column(nullable = false)
    private boolean obavijestiNaMail;

    @ManyToMany(targetEntity = Obavijest.class)
    private List<Obavijest> obavijesti;

    public void addObavijest(Obavijest obavijest){
        obavijesti.add(obavijest);
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
}
