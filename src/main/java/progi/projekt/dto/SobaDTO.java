package progi.projekt.dto;

import lombok.Data;
import progi.projekt.model.Soba;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;

import java.util.UUID;

@Data
public class SobaDTO {
    private UUID id;
    private PaviljonDTO paviljon;
    private int kat;
    private BrojKrevetaEnum brojKreveta;
    private TipKupaoniceEnum tipKupaonice;
    private String komentar;

    public SobaDTO(Soba soba) {
        this.id = soba.getId();
        this.paviljon = new PaviljonDTO(soba.getPaviljon());
        this.kat = soba.getKat();
        this.brojKreveta = soba.getBrojKreveta();
        this.tipKupaonice = soba.getTipKupaonice();
        this.komentar = soba.getKomentar();
    }

}
