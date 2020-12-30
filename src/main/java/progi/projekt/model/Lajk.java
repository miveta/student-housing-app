package progi.projekt.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
//@IdClass(LajkId.class)
public class Lajk implements Serializable {
    private int ocjena;

    @EmbeddedId
    private LajkId lajkId;

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    public LajkId getLajkId() {
        return lajkId;
    }

    public void setLajkId(LajkId lajkId) {
        this.lajkId = lajkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lajk lajk = (Lajk) o;
        return ocjena == lajk.ocjena &&
                Objects.equals(lajkId, lajk.lajkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ocjena, lajkId);
    }

}
