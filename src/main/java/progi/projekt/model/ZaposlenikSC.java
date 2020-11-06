package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class ZaposlenikSC {
    @Id
    @Column(name = "id_zaposlenik")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

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

    @ManyToOne
    @JoinColumn(name = "id_sc")
    private StudentskiCentar zaposlenSC;

    public ZaposlenikSC() {
    }

    //Ništa ne smije biti null!
    public ZaposlenikSC(String korisnickoIme, String ime, String prezime, String email, String lozinka) {
        if (korisnickoIme != null && ime != null && prezime != null && email != null && lozinka != null) {
            this.korisnickoIme = korisnickoIme;
            this.ime = ime;
            this.prezime = prezime;
            this.email = email;
            this.lozinka = lozinka;
        } else {
            System.err.println("Pri kreaciji zaposlenika nista ne smije biti null!");
        }
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StudentskiCentar getZaposlenSC() {
        return zaposlenSC;
    }

    public void setZaposlenSC(StudentskiCentar zaposlenSC) {
        this.zaposlenSC = zaposlenSC;
    }
}
