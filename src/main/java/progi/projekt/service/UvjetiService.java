package progi.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.repository.KandidatRepository;
import progi.projekt.repository.UvjetiRepository;

import java.util.UUID;

public interface UvjetiService {

	static TrazeniUvjeti findByOglas(Oglas oglas) {
		return null;
	}

	static Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti) {
		//poziv non-static
		return null;
	}

	static Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
		if (	soba.getBrojKreveta() == uvjeti.getBrojKreveta() &&
				soba.getKategorija() == uvjeti.getKategorija() &&
				soba.getTipKupaonice() == uvjeti.getTipKupaonice()
		){
			return true;
		} else {
			return false;
		}
	}
}
