package progi.projekt.service;

import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;

import java.util.UUID;

public interface UvjetiService {
	static TrazeniUvjeti findByIdOglas(UUID id) {
		//poziv non-static
		return null;
	}

	static Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti) {
		//poziv non-static
		return null;
	}

	static Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
		//poziv non-static
		return null;
	}
}
