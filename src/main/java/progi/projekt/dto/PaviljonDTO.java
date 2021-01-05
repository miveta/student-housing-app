package progi.projekt.dto;

import progi.projekt.model.Paviljon;
import progi.projekt.model.enums.OznakeKategorijaEnum;

import java.util.UUID;

public class PaviljonDTO {
    private UUID id;
    private String naziv;
    private OznakeKategorijaEnum kategorija;
    private int brojKatova;

    public PaviljonDTO(Paviljon paviljon){
        this.id = paviljon.getId();
        this.naziv = paviljon.getNaziv();
        this.kategorija = paviljon.getKategorija();
        this.brojKatova = paviljon.getBrojKatova();
    }

    public int getBrojKatova() {
        return brojKatova;
    }

    public void setBrojKatova(int brojKatova) {
        this.brojKatova = brojKatova;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public OznakeKategorijaEnum getKategorija() {
        return kategorija;
    }

    public void setKategorija(OznakeKategorijaEnum kategorija) {
        this.kategorija = kategorija;
    }
}
