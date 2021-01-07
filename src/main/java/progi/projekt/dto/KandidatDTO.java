package progi.projekt.dto;

import progi.projekt.model.Kandidat;

import java.util.UUID;

public class KandidatDTO {
    private UUID id;
    private OglasDTO kandOglas;
    private Integer bliskost;
    private Integer kandOcjena;

    public KandidatDTO(Kandidat kandidat) {
        this.id = kandidat.getIdKandidat();
        this.bliskost = kandidat.getBliskost();
        this.kandOcjena = kandidat.getOcjena2();
        this.kandOglas = new OglasDTO(kandidat.getOglas());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OglasDTO getKandOglas() {
        return kandOglas;
    }

    public void setKandOglas(OglasDTO kandOglas) {
        this.kandOglas = kandOglas;
    }

    public Integer getBliskost() {
        return bliskost;
    }

    public void setBliskost(Integer bliskost) {
        this.bliskost = bliskost;
    }

    public Integer getKandOcjena() {
        return kandOcjena;
    }

    public void setKandOcjena(Integer kandOcjena) {
        this.kandOcjena = kandOcjena;
    }
}
