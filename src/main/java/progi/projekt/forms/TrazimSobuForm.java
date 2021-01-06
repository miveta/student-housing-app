package progi.projekt.forms;

import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

public class TrazimSobuForm {
    private String studentUsername;
    private String[] domId;
    private String[] paviljoni;
    private Integer[] katovi;
    private BrojKrevetaEnum[] brojKreveta;
    private TipKupaoniceEnum[] tipKupaonice;

    public TrazimSobuForm(String studentUsername, String[] domId, String[] paviljoni, Integer[] katovi, BrojKrevetaEnum[] brojKreveta, TipKupaoniceEnum[] tipKupaonice) {
        this.studentUsername = studentUsername;
        this.domId = domId;
        this.paviljoni = paviljoni;
        this.katovi = katovi;
        this.brojKreveta = brojKreveta;
        this.tipKupaonice = tipKupaonice;
    }

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

}
