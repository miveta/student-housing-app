package progi.projekt.model.enums;

//AKTIVAN- slobodan
//CEKA- rezerviran i stoju u Paru, ceka potvrdu korisnika
//OBRISAN- korisnik je obrisao ovaj oglas
//ARHIVIRAN- oglas je iz prijasnih godina, vise nije validan. Mozda se nece koristiti
//POTVRDEN- neki student je potvrdio ovaj oglas, te se ceka da zaposlenik SC-a potvrdi zamjenu
//IZVEDEN- oglas je proveden od strane SC-a
public enum StatusOglasaEnum {
    AKTIVAN, CEKA, OBRISAN, ARHIVIRAN, POTVRDEN, IZVEDEN
}
