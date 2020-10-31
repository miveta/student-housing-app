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
}