package progi.projekt.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//TODO: CHECK
@Entity
public class Obavijest {
    @Id
    @Column(name = "id_obavijest")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String tekst;

    private boolean procitana;
    private Date vrijeme;

    @ManyToMany(targetEntity = Korisnik.class, mappedBy = "obavijesti")
    private List<Korisnik> korisnici;

    @ManyToOne
    @JoinColumn(name="id_oglas")
    private Oglas oglas;

    //Tekst ne smije biti null
    public Obavijest(String tekst, Date vrijeme){
        if(tekst != null){
            this.tekst = tekst;
            this.procitana = false;
            this.vrijeme = vrijeme;
        }
        else
            System.err.println("Tekst obavijesti ne smije biti null1");
    }

    public void addKorisnik(Korisnik kor){
        korisnici.add(kor);
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public boolean isProcitana() {
        return procitana;
    }

    public void setProcitana(boolean procitana) {
        this.procitana = procitana;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }
}
