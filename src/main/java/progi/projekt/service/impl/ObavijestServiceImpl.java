package progi.projekt.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import progi.projekt.model.Obavijest;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.repository.ObavijestRepository;
import progi.projekt.service.ObavijestService;
import progi.projekt.service.StudentService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObavijestServiceImpl implements ObavijestService {
    private StudentService studentService;
    private ObavijestRepository obavijestRepository;
    private JavaMailSender javaMailSender;

    public ObavijestServiceImpl(StudentService studentService, ObavijestRepository obavijestRepository, JavaMailSender javaMailSender) {
        this.studentService = studentService;
        this.obavijestRepository = obavijestRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void notifyRoomConstraintsNoLongerValid(List<Student> studenti, Oglas oglas) {/*
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglas);
        obavijest.setStudent(studenti);
        obavijest.setTekst("Oglas studenta " + oglas.getStudent().getIme() + " " + oglas.getStudent().getPrezime()
                + " je promjenjen, te više ne paše vašim uvjetima.");
        obavijestRepository.save(obavijest);

        //Posalji mail ako je ukljuceno automatsko slanje
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("Uvjeti sobe više ne pašu vašim zahtjevima");
        msg.setText(obavijest.getTekst());
        for (Student student : studenti) {
            if (student.isObavijestiNaMail()) {
                msg.setTo(student.getEmail());
                javaMailSender.send(msg);
            }
        }*/
    }

    @Override
    public void notifyRoomConstraintsChanged(Oglas oglas) {
/*Obavijest obavijest = new Obavijest();
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

        //Posalji mail ako je ukljuceno automatsko slanje
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("Uvjeti sobe su se promjenili!");
        msg.setText(obavijest.getTekst());
        for(Student student:studentiLiked){
            if(student.isObavijestiNaMail()){
                msg.setTo(student.getEmail());
                javaMailSender.send(msg);
            }
        }*/
    }

    @Override
    public void notifyLiked(Oglas oglas, Student student) {
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglas);
        ArrayList<Student> students = new ArrayList<>();
        students.add(oglas.getStudent());
        obavijest.setStudent(students);
        obavijest.setTekst(student.getIme() + " " + student.getPrezime() + " je lajkao/la Vašu sobu!");
        obavijest = obavijestRepository.save(obavijest);

        Obavijest finalObavijest = obavijest;
        for (Student s : students) {
            if(s.getObavijesti() == null) s.setObavijesti(new ArrayList<>());
            s.getObavijesti().add(finalObavijest);
            studentService.save(s);
        }

        //Posalji mail ako je ukljuceno automatsko slanje
        if (student.isObavijestiNaMail()) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("Netko je lajkao/la Vašu sobu");
            msg.setText(obavijest.getTekst());
            msg.setTo(student.getEmail());
            javaMailSender.send(msg);
        }
    }

    //todo: test obadva!!!
    @Override
    public void notifyWaiting(Oglas oglasZamijenjenog, Oglas oglasZamjene) {
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglasZamjene);
        ArrayList<Student> students = new ArrayList<>();
        students.add(oglasZamijenjenog.getStudent());
        obavijest.setStudent(students);
        obavijest.setTekst("Čekate zamjenu sobe sa " + oglasZamjene.getStudent().getIme() + " " +
                oglasZamjene.getStudent().getPrezime() + "!");
        obavijestRepository.save(obavijest);

        students.forEach(s -> {
            if(s.getObavijesti() == null) s.setObavijesti(new ArrayList<>());
            s.getObavijesti().add(obavijest);
            studentService.save(s);
        });

        //Posalji mail ako je ukljuceno automatsko slanje
        if (oglasZamijenjenog.getStudent().isObavijestiNaMail()) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("Zamjena sobe je u čekanju");
            msg.setText(obavijest.getTekst());
            msg.setTo(oglasZamijenjenog.getStudent().getEmail());
            javaMailSender.send(msg);
        }
    }

    @Override
    public void notifyExchanged(Oglas oglasZamijenjenog, Oglas oglasZamjene) {
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglasZamjene);
        ArrayList<Student> students = new ArrayList<>();
        students.add(oglasZamijenjenog.getStudent());
        obavijest.setStudent(students);
        obavijest.setTekst("Vaša soba je uspješno zamijenjena sa sobom " + oglasZamjene.getStudent().getIme() + " " +
                oglasZamjene.getStudent().getPrezime() + "!");
        obavijestRepository.save(obavijest);

        students.forEach(s -> {
            if(s.getObavijesti() == null) s.setObavijesti(new ArrayList<>());
            s.getObavijesti().add(obavijest);
            studentService.save(s);
        });

        //Posalji mail ako je ukljuceno automatsko slanje
        if (oglasZamijenjenog.getStudent().isObavijestiNaMail()) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("Zamjena sobe je potvrđena");
            msg.setText(obavijest.getTekst());
            msg.setTo(oglasZamijenjenog.getStudent().getEmail());
            javaMailSender.send(msg);
        }
    }

    @Override
    public boolean oznaciProcitana(UUID id) {
        Optional<Obavijest> optionalObavijest = obavijestRepository.findById(id);
        if (optionalObavijest.isEmpty()) return false;

        Obavijest obavijest = optionalObavijest.get();
        if (!obavijest.isProcitana()) obavijest.setProcitana(true);

        try {
            obavijestRepository.save(obavijest);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
