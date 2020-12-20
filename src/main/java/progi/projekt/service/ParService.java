package progi.projekt.service;

import progi.projekt.model.Oglas;
import progi.projekt.model.Par;

import java.util.List;

public interface ParService {
	List<Par> listAll();

	Integer ObostranaOcjena(Integer ocjena1, Integer ocjena2);

	boolean parSadrziOglas(Par par, Oglas oglas);

	boolean oglasiSuAKTIVAN(Par par);

	void rezervirajOglasePara(Par par);

	void save(Par par);

	boolean ifObaAKTIVAN(Par par);
}
