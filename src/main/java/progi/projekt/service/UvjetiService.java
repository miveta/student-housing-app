package progi.projekt.service;

import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.model.Paviljon;
import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;

@Service
public interface UvjetiService {

    default TrazeniUvjeti findByOglas(Oglas oglas) {
        return null;
    }

    default Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti) {
        //poziv non-static
        return null;
    }

    default Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
        Paviljon p = new Paviljon();

        if (soba.getBrojKreveta() == uvjeti.getBrojKreveta() &&
                soba.getPaviljon().getKategorija() == uvjeti.getKategorija() &&
                soba.getTipKupaonice() == uvjeti.getTipKupaonice()
        ) {
            return true;
        } else {
            return false;
        }
    }
}
