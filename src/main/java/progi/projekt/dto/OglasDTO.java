package progi.projekt.dto;

import progi.projekt.model.Oglas;
import progi.projekt.model.enums.StatusOglasaEnum;

import java.sql.Date;
import java.util.UUID;

public class OglasDTO {
    private UUID id;
    private String naslov;
    private String opis;
    private int godina;
    private Date objavljen;
    private StatusOglasaEnum status;
    private String student;

    public OglasDTO(Oglas oglas) {
        this.id = oglas.getId();
        this.naslov = oglas.getNaslov();
        this.objavljen = oglas.getObjavljen();
        this.status = oglas.getStatusOglasa();
        this.student = oglas.getStudent().getKorisnickoIme();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public Date getObjavljen() {
        return objavljen;
    }

    public void setObjavljen(Date objavljen) {
        this.objavljen = objavljen;
    }

    public StatusOglasaEnum getStatus() {
        return status;
    }

    public void setStatus(StatusOglasaEnum status) {
        this.status = status;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
