package progi.projekt.forms;

import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import java.util.Calendar;

public class TrazimSobuForm {
    private String studentUsername;
    private String[] domId;
    private String[] paviljoni;
    private Integer[] katovi;
    private BrojKrevetaEnum[] brojKreveta;
    private TipKupaoniceEnum[] tipKupaonice;

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
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

    public void fromTrazeniUvjetiForm(TrazeniUvjeti trazeniUvjeti) {
        trazeniUvjeti.setGodina(Calendar.getInstance().get(Calendar.YEAR));
    }
}
