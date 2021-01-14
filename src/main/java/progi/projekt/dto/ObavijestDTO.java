package progi.projekt.dto;

import progi.projekt.model.Obavijest;

import java.sql.Date;
import java.util.UUID;

public class ObavijestDTO {
    private UUID id;
    private String tekst;
    private boolean procitana;
    private Date vrijeme;
    private UUID oglasId;

    public ObavijestDTO(Obavijest obavijest) {
        this.id = obavijest.getId();
        this.tekst = obavijest.getTekst();
        this.procitana = obavijest.isProcitana();
        this.vrijeme = obavijest.getVrijeme();
        if (obavijest.getOglas() != null)
            this.oglasId = obavijest.getOglas().getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public boolean isProcitana() {
        return procitana;
    }

    public void setProcitana(boolean procitana) {
        this.procitana = procitana;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    public UUID getOglasId() {
        return oglasId;
    }

    public void setOglasId(UUID oglasId) {
        this.oglasId = oglasId;
    }
}
