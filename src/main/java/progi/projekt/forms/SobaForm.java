package progi.projekt.forms;

import progi.projekt.model.Soba;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

public class SobaForm {
    private String studentUsername;
    private String idPaviljon;
    private int kat;
    private BrojKrevetaEnum brojKreveta;
    private TipKupaoniceEnum tipKupaonice;
    private String komentar;

    public SobaForm(String studentUsername, String idPaviljon, int kat, BrojKrevetaEnum brojKreveta, TipKupaoniceEnum tipKupaonice, String komentar) {
        this.studentUsername = studentUsername;
        this.idPaviljon = idPaviljon;
        this.kat = kat;
        this.brojKreveta = brojKreveta;
        this.tipKupaonice = tipKupaonice;
        this.komentar = komentar;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getIdPaviljon() {
        return idPaviljon;
    }

    public void setIdPaviljon(String idPaviljon) {
        this.idPaviljon = idPaviljon;
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

    public void fromSobaForm(Soba soba) {
        soba.setBrojKreveta(this.brojKreveta);
        soba.setTipKupaonice(this.tipKupaonice);
        soba.setKat(this.kat);
        soba.setKomentar(this.komentar);
    }
}
