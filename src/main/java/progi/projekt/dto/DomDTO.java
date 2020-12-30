package progi.projekt.dto;

import progi.projekt.model.Dom;

import java.util.Objects;
import java.util.UUID;


public class DomDTO {
    private UUID id;
    private boolean imaMenzu;
    private String naziv;

    public DomDTO(Dom dom) {
        this.id = dom.getId();
        this.imaMenzu = dom.isImaMenzu();
        this.naziv = dom.getNaziv();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isImaMenzu() {
        return imaMenzu;
    }

    public void setImaMenzu(boolean imaMenzu) {
        this.imaMenzu = imaMenzu;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomDTO domDTO = (DomDTO) o;
        return imaMenzu == domDTO.imaMenzu &&
                id.equals(domDTO.id) &&
                naziv.equals(domDTO.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imaMenzu, naziv);
    }

}
