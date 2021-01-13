package progi.projekt.dto;

import progi.projekt.model.Oglas;
import progi.projekt.model.enums.StatusOglasaEnum;

import java.sql.Date;
import java.util.UUID;

public class OglasDTO {
    private UUID id;
    private String naslov;
    private String opis;
    private Date objavljen;
    private StatusOglasaEnum status;
    private String student;
    private String studentJmbag;
    private String studentEmail;
    private SobaDTO soba;
    private UvjetiDTO uvjeti;



    public OglasDTO(Oglas oglas) {
        this.id = oglas.getId();
        this.naslov = oglas.getNaslov();
        this.objavljen = oglas.getObjavljen();
        this.status = oglas.getStatusOglasa();
        this.student = oglas.getStudent().getKorisnickoIme();
        this.studentJmbag = oglas.getStudent().getJmbag();
        this.studentEmail = oglas.getStudent().getEmail();
        this.soba = new SobaDTO(oglas.getSoba());
        this.uvjeti = new UvjetiDTO(oglas.getTrazeniUvjeti());
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

    public String getStudentJmbag() { return studentJmbag; }

    public void setStudentJmbag(String studentJmbag) { this.studentJmbag = studentJmbag; }

    public String getStudentEmail() { return studentEmail; }

    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public SobaDTO getSoba() {
        return soba;
    }

    public void setSoba(SobaDTO soba) {
        this.soba = soba;
    }

    public UvjetiDTO getUvjeti() {
        return uvjeti;
    }

    public void setUvjeti(UvjetiDTO uvjeti) {
        this.uvjeti = uvjeti;
    }
}
