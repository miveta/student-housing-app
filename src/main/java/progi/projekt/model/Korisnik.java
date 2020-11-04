package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

//TODO: INHERITANCE?????
public class Korisnik {
    @Id
    @Column(name = "id_korisnik")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    // Korisnicko ime ne smije biti null, te mora biti unique
    @Column(nullable = false,unique = true, name = "korisnicko_ime")
    protected String korisnickoIme;

    @Column(nullable = false)
    protected String ime;

    @Column(nullable = false)
    protected String prezime;

    @Column(nullable = false)
    protected String email;

    @Column(nullable = false, name = "hash_lozinke")
    protected String lozinka;

    @Column(nullable = false)
    protected boolean obavijestiNaMail;

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

}
