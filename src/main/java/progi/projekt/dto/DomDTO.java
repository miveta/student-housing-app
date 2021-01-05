package progi.projekt.dto;

import progi.projekt.model.Dom;
import progi.projekt.model.Paviljon;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class DomDTO {
    private UUID id;
    private boolean imaMenzu;
    private String naziv;
    private Set<PaviljonDTO> paviljoni = new HashSet<>();

    public DomDTO(Dom dom) {
        this.id = dom.getId();
        this.imaMenzu = dom.isImaMenzu();
        this.naziv = dom.getNaziv();
        dom.getPaviljoni().forEach(paviljon -> this.paviljoni.add(new PaviljonDTO(paviljon)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomDTO domDTO = (DomDTO) o;
        return imaMenzu == domDTO.imaMenzu &&
                Objects.equals(id, domDTO.id) &&
                Objects.equals(naziv, domDTO.naziv) &&
                Objects.equals(paviljoni, domDTO.paviljoni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imaMenzu, naziv, paviljoni);
    }

    public Set<PaviljonDTO> getPaviljoni() {
        return paviljoni;
    }

    public void setPaviljoni(Set<PaviljonDTO> paviljoni) {
        this.paviljoni = paviljoni;
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

}
