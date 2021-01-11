package progi.projekt.dto;

import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

public class UvjetiDTO {
    private DomDTO[] domovi;
    private String[] paviljoni;
    private Integer[] katovi;
    private BrojKrevetaEnum[] brojKreveta;
    private TipKupaoniceEnum[] tipKupaonice;

    public UvjetiDTO(TrazeniUvjeti trazeniUvjeti) {
        try {
            this.domovi = trazeniUvjeti.getDomovi().stream().map(DomDTO::new).toArray(DomDTO[]::new);
        } catch (NullPointerException ex) {
        }
        try {
            this.paviljoni = trazeniUvjeti.getPaviljoni().stream().map(p -> p.getId().toString()).toArray(String[]::new);
        } catch (NullPointerException ex) {
        }
        try {
            this.katovi = trazeniUvjeti.getKatovi().toArray(Integer[]::new);
        } catch (NullPointerException ex) {
        }
        try {
            this.brojKreveta = trazeniUvjeti.getBrojKreveta().toArray(BrojKrevetaEnum[]::new);
        } catch (NullPointerException ex) {
        }
        try {
            this.tipKupaonice = trazeniUvjeti.getTipKupaonice().toArray(TipKupaoniceEnum[]::new);
        } catch (NullPointerException ex) {
        }

    }

    public DomDTO[] getDomovi() {
        return domovi;
    }

    public void setDomovi(DomDTO[] domovi) {
        this.domovi = domovi;
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
