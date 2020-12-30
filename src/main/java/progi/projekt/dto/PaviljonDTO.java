package progi.projekt.dto;

import progi.projekt.model.Dom;
import progi.projekt.model.Paviljon;
import progi.projekt.model.Soba;

import java.util.Set;
import java.util.UUID;

public class PaviljonDTO {
    private UUID id;
    private String naziv;
    private Dom dom;
    private Set<Soba> sobe;

    public PaviljonDTO(Paviljon paviljon) {
        this.id = paviljon.getId();
        this.dom = paviljon.getDom();
        this.naziv = paviljon.getNaziv();
        this.sobe = paviljon.getSobe();
    }

    public Set<Soba> getSobe() { return sobe; }

    public void setSobe(Set<Soba> sobe) { this.sobe = sobe; }

    public Dom getDom() { return dom; }

    public void setDom(Dom dom) { this.dom = dom; }

    public String getNaziv() { return naziv; }

    public void setNaziv(String naziv) { this.naziv = naziv; }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id;}
}
