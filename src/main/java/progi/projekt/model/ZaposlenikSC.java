package progi.projekt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

//TODO: INHERITANCE?????????
@Entity
public class ZaposlenikSC{
    @Id
    private UUID interniID;
}
