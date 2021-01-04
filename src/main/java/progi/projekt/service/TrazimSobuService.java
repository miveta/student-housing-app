package progi.projekt.service;

import progi.projekt.model.Dom;
import progi.projekt.model.Grad;
import progi.projekt.model.Paviljon;
import progi.projekt.model.TrazeniUvjeti;

import java.util.List;
import java.util.Set;

public interface TrazimSobuService {

    Set<Dom> findAllDom();
    List<Paviljon> findAllPaviljon();
    Grad findGrad(String username);
    void update(TrazeniUvjeti uvjeti);
}
