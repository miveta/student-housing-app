package progi.projekt.model;

import javax.persistence.*;

@Entity
@Table
public class Student {
    @Id
    @Column(name = "id_student")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
