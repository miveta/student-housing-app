package progi.projekt.dto;

import progi.projekt.model.Par;
import progi.projekt.model.ZaposlenikSC;

public class ParDTO {
    private long parID;
    private OglasDTO oglas1;
    private OglasDTO oglas2;
    private Boolean potvrdioPrvi;
    private Boolean potvrdioDrugi;
    private Boolean done;
    private Boolean lanac;
    private Boolean ignore;
    private Boolean odobren;
    private ZaposlenikSC zaposlenikSC;

    public ParDTO(Par par) {
        this.parID = par.getIdPar();
        this.oglas1 = new OglasDTO(par.getOglas1());
        this.oglas2 = new OglasDTO(par.getOglas2());
        this.done = par.getDone();
        this.lanac = par.getLanac();
        this.ignore = par.getIgnore();
        this.odobren = par.getOdobren();
        this.zaposlenikSC = par.getZaposlenikSC();
        this.potvrdioDrugi = par.getPrihvatioDrugi();
        this.potvrdioPrvi = par.getPrihvatioPrvi();
    }

    public long getParID() {
        return parID;
    }

    public void setParID(long parID) {
        this.parID = parID;
    }

    public OglasDTO getOglas1() {
        return oglas1;
    }

    public void setOglas1(OglasDTO oglas1) {
        this.oglas1 = oglas1;
    }

    public OglasDTO getOglas2() {
        return oglas2;
    }

    public void setOglas2(OglasDTO oglas2) {
        this.oglas2 = oglas2;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getLanac() {
        return lanac;
    }

    public void setLanac(Boolean lanac) {
        this.lanac = lanac;
    }

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public Boolean getOdobren() {
        return odobren;
    }

    public void setOdobren(Boolean odobren) {
        this.odobren = odobren;
    }

    public ZaposlenikSC getZaposlenikSC() {
        return zaposlenikSC;
    }

    public void setZaposlenikSC(ZaposlenikSC zaposlenikSC) {
        this.zaposlenikSC = zaposlenikSC;
    }

    public Boolean getPotvrdioPrvi() {
        return potvrdioPrvi;
    }

    public void setPotvrdioPrvi(Boolean potvrdioPrvi) {
        this.potvrdioPrvi = potvrdioPrvi;
    }

    public Boolean getPotvrdioDrugi() {
        return potvrdioDrugi;
    }

    public void setPotvrdioDrugi(Boolean potvrdioDrugi) {
        this.potvrdioDrugi = potvrdioDrugi;
    }
}
