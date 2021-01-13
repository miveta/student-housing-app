package progi.projekt.model;

import javax.persistence.*;

@Entity
public class Par {
    @Id
    @Column(name = "id_par", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idPar;

    //@Column(name = "id_oglas1", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oglas1")
    private Oglas oglas1;

    //@Column(name = "id_oglas2", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oglas2")
    private Oglas oglas2;

    @Basic
    @Column(name = "done", nullable = true)
    private Boolean done;

    @Basic
    @Column(name = "odobren", nullable = true)
    private Boolean odobren;


    @Basic
    @Column(name = "lanac", nullable = true)
    private Boolean lanac;

    @Basic
    @Column(name = "ignore", nullable = true)
    private Boolean ignore;

    @Basic
    @Column(name = "prihvatioPrvi", nullable = true)
    private Boolean prihvatioPrvi;

    @Basic
    @Column(name = "prihvatioDrugi", nullable = true)
    private Boolean prihvatioDrugi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zaposlenikKorisnickoIme")
    private ZaposlenikSC zaposlenikSC;


    public Par(Oglas oglas1, Oglas oglas2) {
        if (oglas1 != null || oglas2 != null) {
            this.oglas1 = oglas1;
            this.oglas2 = oglas2;
            this.done = false;
            this.lanac = false;
            this.ignore = false;
        } else
            System.err.print("Dani oglasi ne smiju biti null!");
    }

    public Par(Oglas oglas1, Oglas oglas2, Boolean done, Boolean lanac, Boolean ignore) {
        if (oglas1 != null || oglas2 != null) {
            this.oglas1 = oglas1;
            this.oglas2 = oglas2;
            this.done = done;
            this.lanac = lanac;
            this.ignore = ignore;
        } else
            System.err.print("Dani oglasi ne smiju biti null!");
    }

    public Par() {
    }

    public long getIdPar() {
        return idPar;
    }

    public void setIdPar(long idPar) {
        this.idPar = idPar;
    }

    public Oglas getOglas1() {
        return oglas1;
    }

    public void setOglas1(Oglas oglas1) {
        this.oglas1 = oglas1;
    }

    public Oglas getOglas2() {
        return oglas2;
    }

    public void setOglas2(Oglas oglas2) {
        this.oglas2 = oglas2;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getOdobren() {
        return odobren;
    }

    public void setOdobren(Boolean odobren) {
        this.odobren = odobren;
    }

    public Boolean getLanac() {
        return lanac;
    }

    public void setLanac(Boolean lanac) {
        this.lanac = lanac;
    }

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public Boolean getPrihvatioPrvi() {
        return prihvatioPrvi;
    }

    public void setPrihvatioPrvi(Boolean prihvatioPrvi) {
        this.prihvatioPrvi = prihvatioPrvi;
    }

    public Boolean getPrihvatioDrugi() {
        return prihvatioDrugi;
    }

    public void setPrihvatioDrugi(Boolean prihvatioDrugi) {
        this.prihvatioDrugi = prihvatioDrugi;
    }

    public ZaposlenikSC getZaposlenikSC() {
        return zaposlenikSC;
    }

    public void setZaposlenikSC(ZaposlenikSC zaposlenikSC) {
        this.zaposlenikSC = zaposlenikSC;
    }
}
