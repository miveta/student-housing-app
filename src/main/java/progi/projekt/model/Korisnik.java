package progi.projekt.model;

public interface Korisnik {
    String getJmbag();

    String getKorisnickoIme();

    String getIme();

    String getPrezime();

    boolean isObavijestiNaMail();

    String getEmail();

    String getTipKorisnika();

    Grad getGrad();

}
