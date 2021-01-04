package progi.projekt.service;

import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;

public interface UvjetiService {

    default TrazeniUvjeti findByOglas(Oglas oglas) {
        return null;
    }

    default Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti) {
        //poziv non-static
        return null;
    }

    default Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
        if (soba.getBrojKreveta() == uvjeti.getBrojKreveta() &&
                soba.getKategorija() == uvjeti.getKategorija() &&
                soba.getTipKupaonice() == uvjeti.getTipKupaonice()
        ) {
            return true;
        } else {
            return false;
        }
    }
}
