package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Student {
    @Id
    @Column(name = "id_student")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // sekundarni ključ
    @Column(nullable = false, name = "korisnicko_ime")
    private String korisnickoIme;

    // sekundarni ključ
    @Column(nullable = false, length = 10)
    private String jmbag;

    private String ime;
    private String prezime;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false, name = "hash_lozinke")
    private String lozinka;


    @Column(nullable = false, name = "vise_ovlasti")
    private boolean elevated;
    //ako je ovo true user prilikom logina dobije i ROLE_ADMIN uz ROLE_STUDENT

    //bi li studentService, odnosno StudentRepository trebali raditi ovo?
    public boolean isElevated() {
        return elevated;
    }

    public void setElevated(boolean elevated) {
        this.elevated = elevated;
    }

    public String getLozinka() { return lozinka; }

    public void setLozinka(String lozinka) { this.lozinka = lozinka; }

}
