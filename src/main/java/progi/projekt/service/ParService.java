package progi.projekt.service;

import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;

import java.util.List;
import java.util.Optional;

public interface ParService {
	List<Par> listAll();

	List<Par> listAll(Oglas oglas);

	Optional<Par> find(Long idPar);

	void ponistiPar(Par par);

	Integer ObostranaOcjena(Integer ocjena1, Integer ocjena2);

	boolean parSadrziOglas(Par par, Oglas oglas);

	void rezervirajOglasePara(Par par);

	void potvrdiOglasePara(Par par);

	void vratiOglaseParaNaAKTIVAN(Par par);

	boolean save(Par par);

	void update(Par par);

	boolean ifObaAKTIVAN(Par par);

	boolean ifObaCEKA(Par par);

	Integer TrostranaOcjena(Integer ocjenaAB, Integer ocjenaBC, Integer ocjenaCA);

	List<Oglas> pronadjiTreciOglasIzLanca(Par par);

	List<Oglas> pripadniOglasiLanca(Oglas oglas1);

	List<Par> pripadniParoviLanca(Oglas oglas1);

	Optional<Par> pripadniParDvaOglasa(Oglas oglas1, Oglas oglas2);

	List<Par> pripadniParoviOglasa(Oglas oglas);

	Optional<Par> pripadniParAB(Oglas oglasA, Oglas oglasB);

	Optional<Kandidat> pripadniKandidatPara(Par par);

	void ponistiParoveOglasa(Oglas oglas);

	boolean obaStudPrihvatila(Par par);

	boolean josNisuPar(Oglas oglas, Oglas kand);

	boolean josNisuLanac(Oglas oglas, Oglas kand);
}
