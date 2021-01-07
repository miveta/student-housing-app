package progi.projekt.service;

import progi.projekt.model.Par;

import java.util.List;

public interface MatchingService {
	void kandidatiFun();
	void lajkFun();
	void parFun();
	void matchFun();
	void confirmFun();
	void confirmSCFun(List<Par> izvedeni);
	void confirmSCFun();
}
