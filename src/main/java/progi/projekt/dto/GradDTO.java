package progi.projekt.dto;

import progi.projekt.model.Grad;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class GradDTO implements Comparable {
    private UUID id;
    private String naziv;
    private Set<DomDTO> domovi = new HashSet<>();

    public GradDTO(Grad grad) {
        this.id = grad.getId();
        this.naziv = grad.getNaziv();
        grad.getDomovi().forEach(dom -> this.domovi.add(new DomDTO(dom)));
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<DomDTO> getDomovi() {
        return domovi;
    }

    public void setDomovi(Set<DomDTO> domovi) {
        this.domovi = domovi;
    }

    @Override
    public int compareTo(Object o) {
        UUID prviId = this.getId();

        GradDTO drugi = (GradDTO) o;
        UUID drugiId = drugi.getId();

        return prviId.compareTo(drugiId);
    }
}
