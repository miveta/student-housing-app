package progi.projekt.service;

import progi.projekt.model.Oglas;
import progi.projekt.model.Student;

import java.util.List;
import java.util.UUID;

public interface ObavijestService {
    //Stvara obavijest/i ako se soba promijeni i ne pase vise tom korisniku/ima
    void notifyRoomConstraintsNoLongerValid(List<Student> studenti, Oglas oglas);

    //Daje obavijest svima koji su lajkali taj oglas da se soba promjenila
    void notifyRoomConstraintsChanged(Oglas oglas);

    //Slanje obavijesti da ti je netko lajkao sobu
    void notifyLiked(Oglas oglas, Student student);

    //Obavijest potvrde zamjene -- prvi oglas predstavlja studenta kojem je konfirmirana zamijena, a drugi
    //je oglas koji mu je dan na zamijenu
    void notifyExchanged(Oglas oglasZamijenjenog, Oglas oglasZamjene);

    boolean oznaciProcitana(UUID id);
}
