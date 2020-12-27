package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.forms.LajkForm;
import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.service.LajkService;
import progi.projekt.service.OglasService;
import progi.projekt.service.StudentService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/lajk")
public class LajkController {

    @Autowired
    private LajkService lajkService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private OglasService oglasService;

    @GetMapping
    public List<Lajk> listLajks() {
        return lajkService.listAll();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam(value = "id_student") Student studentId,
                                    @RequestParam(value = "id_oglas") Oglas oglasId,
                                    @RequestParam(value = "ocjena") int ocjena) {
    //public ResponseEntity<?> update(@RequestBody LajkForm data) {

        System.out.println("here");
        //int ocjena = data.getOcjena();

       // Optional<Student> studentId = studentService.findBykorisnickoIme(data.getStudentId());
        //Optional<Oglas> oglasId = oglasService.findById(data.getOglasId());

        //LajkId lajkId = new LajkId(studentId.get(), oglasId.get());

        LajkId lajkId = new LajkId(studentId, oglasId);

        Optional<Lajk> lajk = lajkService.findLajk(lajkId);

        Lajk l = lajk.get();
        l.setOcjena(ocjena);

        Lajk updatedLajk = lajkService.update(l);

        return ResponseEntity.ok(null);
    }

}
