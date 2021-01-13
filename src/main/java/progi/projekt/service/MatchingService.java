package progi.projekt.service;

import progi.projekt.model.Par;

import java.util.List;
import java.util.UUID;

public interface MatchingService {
	void kandidatiFun();
	void lajkFun();

	void resetKandsDebug();

	void parFun();
	void matchFun();
	void confirmFun();
	void confirmSCFun();
	void resetirajOglas(UUID oglasId);
	void ponistiPar(Par par);
}
