package progi.projekt.service;

import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;

import java.util.List;
import java.util.Optional;

public interface LajkService {

    List<Lajk> listAll();

    Optional<Lajk> findLajk(LajkId lajkId);

    Lajk update(Lajk l);
}
