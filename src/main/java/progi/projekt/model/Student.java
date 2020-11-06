package progi.projekt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Student implements Serializable {
    @Id
    @Column(name = "id_korisnik")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private int jmbag;

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

    private boolean obavijestiNaMail;


    @ManyToMany(targetEntity = Obavijest.class)
    private List<Obavijest> obavijesti;

    @OneToOne
    @JoinColumn(name="id_status_oglasa")
    private Oglas potvrdioOglas;

    @OneToOne
    @JoinColumn(name = "id_trazeni_uvjeti")
    private TrazeniUvjeti uvjeti;

    public Student() { }

    //Sve osim obavijestiNaMail ne smije biti null!
    //JMBAG mora biti velicine 10
    public Student(int jmbag, String korisnickoIme, String ime, String prezime, String email, String lozinka, boolean obavijestiNaMail){
        if(korisnickoIme != null && ime != null && prezime != null && email != null && lozinka != null){
            if(String.valueOf(jmbag).length() == 10){
                this.jmbag = jmbag;
                this.korisnickoIme = korisnickoIme;
                this.ime = ime;
                this.prezime = prezime;
                this.email = email;
                this.lozinka = lozinka;
                this.obavijestiNaMail = obavijestiNaMail;
            } else {
                System.err.println("Jmbag mora imati 10 znamenaka!");
            }
        } else {
            System.err.println("Pri kreaciji studenta nista ne smije biti null osim obavijestiNaMail!");
        }
    }

    public List<Obavijest> getObavijesti() {
        return obavijesti;
    }

    public void setObavijesti(List<Obavijest> obavijesti) {
        this.obavijesti = obavijesti;
    }

    public int getJmbag() {
        return jmbag;
    }

    public void setJmbag(int jmbag) {
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

    public Oglas getPotvrdioOglas() {
        return potvrdioOglas;
    }

    public void setPotvrdioOglas(Oglas potvrdioOglas) {
        this.potvrdioOglas = potvrdioOglas;
    }

    public TrazeniUvjeti getUvjeti() {
        return uvjeti;
    }

    public void setUvjeti(TrazeniUvjeti uvjeti) {
        this.uvjeti = uvjeti;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
