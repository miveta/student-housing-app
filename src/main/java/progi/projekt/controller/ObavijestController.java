package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import progi.projekt.model.Lajk;
import progi.projekt.model.Obavijest;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.repository.ObavijestRepository;
import progi.projekt.service.OglasService;
import progi.projekt.service.StudentService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ObavijestController {
    @Autowired
    private ObavijestRepository obavijestRepository;

    //Stvara obavijest/i ako se soba promijeni i ne pase vise tom korisniku/ima
    public void notifyRoomConstraintsNoLongerValid(List<Student> studenti, Oglas oglas){
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglas);
        obavijest.setStudent(studenti);
        obavijest.setTekst("Oglas studenta " + oglas.getStudent().getIme() + " " + oglas.getStudent().getPrezime()
                + " je promjenjen, te više ne paše vašim uvjetima.");
        obavijestRepository.save(obavijest);
    }

    //Daje obavijest svima koji su lajkali taj oglas da se soba promjenila
    public void notifyRoomConstraintsChanged(Oglas oglas){
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        ArrayList<Student> studentiLiked = new ArrayList<>();
        for(Lajk lajk: oglas.getLajkovi()) {
            studentiLiked.add(lajk.getLikedByStudent());
        }
        obavijest.setStudent(studentiLiked);
        obavijest.setTekst("Oglas studenta " + oglas.getStudent().getIme() + " "
                + oglas.getStudent().getPrezime() + " izmjenjen.");
        obavijest.setOglas(oglas);
        obavijestRepository.save(obavijest);
    }

    //Slanje obavijesti da ti je netko lajkao sobu
    public void notifyLiked(Oglas oglas, Student student){
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglas);
        ArrayList<Student> students = new ArrayList<>();
        students.add(oglas.getStudent());
        obavijest.setStudent(students);
        obavijest.setTekst(student.getIme() + " " + student.getPrezime() + " je lajkao vašu sobu!");
        obavijestRepository.save(obavijest);
    }

    //Obavijest potvrde zamjene -- prvi oglas predstavlja studenta kojem je konfirmirana zamijena, a drugi
    //je oglas koji mu je dan na zamijenu
    public void notifyExchanged(Oglas oglasZamijenjenog, Oglas oglasZamijene){
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglasZamijene);
        ArrayList<Student> student = new ArrayList<>();
        student.add(oglasZamijenjenog.getStudent());
        obavijest.setStudent(student);
        obavijest.setTekst("Vaša soba je uspješno zamijenjena sa sobom " + oglasZamijene.getStudent().getIme() + " " +
                oglasZamijene.getStudent().getPrezime() + "!");
        obavijestRepository.save(obavijest);
    }
}
