package progi.projekt.dto;


import progi.projekt.model.Soba;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import java.util.UUID;


public class SobaDTO {
    private UUID id;
    private PaviljonDTO paviljon;
    private int kat;
    private BrojKrevetaEnum brojKreveta;
    private TipKupaoniceEnum tipKupaonice;
    private String komentar;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaviljonDTO getPaviljon() {
        return paviljon;
    }

    public void setPaviljon(PaviljonDTO paviljon) {
        this.paviljon = paviljon;
    }

    public int getKat() {
        return kat;
    }

    public void setKat(int kat) {
        this.kat = kat;
    }

    public BrojKrevetaEnum getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(BrojKrevetaEnum brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public TipKupaoniceEnum getTipKupaonice() {
        return tipKupaonice;
    }

    public void setTipKupaonice(TipKupaoniceEnum tipKupaonice) {
        this.tipKupaonice = tipKupaonice;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public SobaDTO(Soba soba) {
        this.id = soba.getId();
        this.paviljon = new PaviljonDTO(soba.getPaviljon());
        this.kat = soba.getKat();
        this.brojKreveta = soba.getBrojKreveta();
        this.tipKupaonice = soba.getTipKupaonice();
        this.komentar = soba.getKomentar();
    }

}
