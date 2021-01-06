package progi.projekt.service;

import progi.projekt.model.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KandidatService {
	List<Kandidat> listAll(UUID oglasUuid);

	void stvoriKand(Oglas oglas1, Oglas oglas2);

	Boolean odgovaraju (Oglas oglas1, Oglas oglas2);

	int odgovaraIizTopN(Oglas oglas);

	List<Oglas> topN (Oglas oglas);

	Boolean josNisuKandidat (Oglas oglas1, Oglas oglas2);

	void save(Kandidat kand);

	Integer calculateScore(Oglas oglas1, Oglas oglas2);

	Optional<Kandidat> kandidatParaOglasa(Oglas oglas1, Oglas oglas2);

	Boolean kandSadrziOglas(Kandidat kand, Oglas oglas);

	void ponistiKandidateOglasa(Oglas oglas);
}
