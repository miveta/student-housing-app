package progi.projekt.service;

import progi.projekt.model.*;

import java.util.List;

public interface KandidatService {
	List<Kandidat> listAll();

	Boolean odgovaraju (Oglas oglas1, Oglas oglas2);

	int odgovaraIizTopN(Oglas oglas);

	List<Oglas> topN (Oglas oglas);

	Boolean josNisuKandidat (Oglas oglas1, Oglas oglas2);
}
