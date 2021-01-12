package progi.projekt.service;

import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;

import java.util.UUID;

public interface UvjetiService {
	TrazeniUvjeti update(TrazeniUvjeti uvjeti);

	TrazeniUvjeti findByIdOglas(UUID id);

	 Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti);

	Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti);
}
