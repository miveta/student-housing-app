package progi.projekt.service;

import progi.projekt.model.Dom;
import progi.projekt.model.Grad;
import progi.projekt.model.Paviljon;

import java.util.List;
import java.util.Set;

public interface TrazimSobuService {
    Grad findGrad(String korisnickoIme);
    Set<Dom> findAllDom();
    Set<Paviljon> findAllPaviljon();
}
