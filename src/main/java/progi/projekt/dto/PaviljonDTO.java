package progi.projekt.dto;

import progi.projekt.model.Dom;
import progi.projekt.model.Paviljon;
import progi.projekt.model.Soba;

import java.util.Set;
import java.util.UUID;

public class PaviljonDTO {
    private UUID id;
    private String naziv;


    public PaviljonDTO(Paviljon paviljon) {
        this.id = paviljon.getId();
        this.naziv = paviljon.getNaziv();
    }



    public String getNaziv() { return naziv; }

    public void setNaziv(String naziv) { this.naziv = naziv; }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id;}
}
