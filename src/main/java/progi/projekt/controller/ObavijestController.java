package progi.projekt.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progi.projekt.model.Obavijest;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.repository.ObavijestRepository;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/obavijesti")
public class ObavijestController {
    private ObavijestRepository obavijestRepository;
    private JavaMailSender javaMailSender;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ObavijestController(ObavijestRepository obavijestRepository, JavaMailSender javaMailSender, SimpMessagingTemplate simpMessagingTemplate) {
        this.obavijestRepository = obavijestRepository;
        this.javaMailSender = javaMailSender;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/greetings")
    public void greet(String greeting) {

        System.out.println("Greeting for" + greeting);
        String text = "[" + Instant.now() + "]: " + greeting;
        this.simpMessagingTemplate.convertAndSend("/topic/greetings", text);
    }


    public Obavijest obavijest() {
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);

        obavijest.setTekst("Oglas studenta " + "oglas.getStudent().getIme()" + " " + "oglas.getStudent().getPrezime()"
                + " je promjenjen, te više ne paše vašim uvjetima.");
        return new Obavijest();
    }


    //Stvara obavijest/i ako se soba promijeni i ne pase vise tom korisniku/ima
    public void notifyRoomConstraintsNoLongerValid(List<Student> studenti, Oglas oglas) {
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
        }
    }

    //Daje obavijest svima koji su lajkali taj oglas da se soba promjenila
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
        msg.setSubject("Uvjeti sobe više ne pašu vašim zahtjevima");
        msg.setText(obavijest.getTekst());
        for(Student student:studentiLiked){
            if(student.isObavijestiNaMail()){
                msg.setTo(student.getEmail());
                javaMailSender.send(msg);
            }
        }*/
    }

    //Slanje obavijesti da ti je netko lajkao sobu
    public void notifyLiked(Oglas oglas, Student student) {
        Obavijest obavijest = new Obavijest();
        obavijest.setVrijeme(new Date(System.currentTimeMillis()));
        obavijest.setProcitana(false);
        obavijest.setOglas(oglas);
        ArrayList<Student> students = new ArrayList<>();
        students.add(oglas.getStudent());
        obavijest.setStudent(students);
        obavijest.setTekst(student.getIme() + " " + student.getPrezime() + " je lajkao vašu sobu!");
        obavijestRepository.save(obavijest);

        //Posalji mail ako je ukljuceno automatsko slanje
        if (student.isObavijestiNaMail()) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("Netko je lajkao vašu sobu");
            msg.setText(obavijest.getTekst());
            msg.setTo(student.getEmail());
            javaMailSender.send(msg);
        }
    }

    //Obavijest potvrde zamjene -- prvi oglas predstavlja studenta kojem je konfirmirana zamijena, a drugi
    //je oglas koji mu je dan na zamijenu
    public void notifyExchanged(Oglas oglasZamijenjenog, Oglas oglasZamijene) {
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

        //Posalji mail ako je ukljuceno automatsko slanje
        if (oglasZamijenjenog.getStudent().isObavijestiNaMail()) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("Zamjena sobe je potvrđena");
            msg.setText(obavijest.getTekst());
            msg.setTo(oglasZamijenjenog.getStudent().getEmail());
            javaMailSender.send(msg);
        }
    }
}
