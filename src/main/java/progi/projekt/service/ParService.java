package progi.projekt.service;

import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;

import java.util.List;
import java.util.Optional;

public interface ParService {
	List<Par> listAll();

	Integer ObostranaOcjena(Integer ocjena1, Integer ocjena2);

	boolean parSadrziOglas(Par par, Oglas oglas);

	void rezervirajOglasePara(Par par);

	void potvrdiOglasePara(Par par);

	void vratiOglaseParaNaAKTIVAN(Par par);

	void save(Par par);

	boolean ifObaAKTIVAN(Par par);

	Integer TrostranaOcjena(Integer ocjenaAB, Integer ocjenaBC, Integer ocjenaCA);

	Optional<Oglas> pronadjiTreciOglasIzLanca(Par par);

	Optional<Par> pripadniParOglasa(Oglas oglas);

	Optional<Kandidat> pripadniKandidatPara(Par par);
}
