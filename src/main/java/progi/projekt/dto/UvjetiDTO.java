package progi.projekt.dto;

import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import java.util.UUID;

public class UvjetiDTO {
    private String[] domId;
    private String[] paviljoni;
    private Integer[] katovi;
    private BrojKrevetaEnum[] brojKreveta;
    private TipKupaoniceEnum[] tipKupaonice;

    public UvjetiDTO(TrazeniUvjeti trazeniUvjeti){
        this.domId = trazeniUvjeti.getDomovi().stream().map(p -> p.getId().toString()).toArray(String[]::new);
        this.paviljoni = trazeniUvjeti.getPaviljoni().stream().map(p -> p.getId().toString()).toArray(String[]::new);
        this.katovi = trazeniUvjeti.getKatovi().toArray(Integer[]::new);
        this.brojKreveta = trazeniUvjeti.getBrojKreveta().toArray(BrojKrevetaEnum[]::new);
        this.tipKupaonice = trazeniUvjeti.getTipKupaonice().toArray(TipKupaoniceEnum[]::new);
    }

    public String[] getDomId() {
        return domId;
    }

    public void setDomId(String[] domId) {
        this.domId = domId;
    }

    public String[] getPaviljoni() {
        return paviljoni;
    }

    public void setPaviljoni(String[] paviljoni) {
        this.paviljoni = paviljoni;
    }

    public Integer[] getKatovi() {
        return katovi;
    }

    public void setKatovi(Integer[] katovi) {
        this.katovi = katovi;
    }

    public BrojKrevetaEnum[] getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(BrojKrevetaEnum[] brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public TipKupaoniceEnum[] getTipKupaonice() {
        return tipKupaonice;
    }

    public void setTipKupaonice(TipKupaoniceEnum[] tipKupaonice) {
        this.tipKupaonice = tipKupaonice;
    }
}
