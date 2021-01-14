package progi.projekt.dto;

import progi.projekt.model.Korisnik;

/*
 * DTO je onaj objekt kojeg šaljemo s backenda na frontend. Kako želimo imati spremljenog
 * trenutnog korisnika kao objekt na frontendu, ali zapravo nam nisu potrebne sve njegove
 * članske varijable - čak za neke poput lozinke ne želimo da budu spremljene, imamo ovaj
 * objekt koji sadrži samo one informacije koje smijemo poslati klijentu. Ostalim
 * informacijama možemo pristupiti preko controllera što je sigurnije.
 * */
public class KorisnikDTO {
    private String jmbag;
    private String korisnickoIme;
    private String ime;
    private String prezime;
    private boolean obavijestiNaMail;
    private String email;
    private String tipKorisnika;
    private GradDTO grad;

    public KorisnikDTO(Korisnik korisnik) {
        this.jmbag = korisnik.getJmbag();
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.obavijestiNaMail = korisnik.isObavijestiNaMail();
        this.email = korisnik.getEmail();
        this.tipKorisnika = korisnik.getTipKorisnika();
        this.grad = new GradDTO(korisnik.getGrad());
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
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

    public boolean isObavijestiNaMail() {
        return obavijestiNaMail;
    }

    public void setObavijestiNaMail(boolean obavijestiNaMail) {
        this.obavijestiNaMail = obavijestiNaMail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(String tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public GradDTO getGrad() { return grad; }

    public void setGrad(GradDTO grad) { this.grad = grad; }

}
