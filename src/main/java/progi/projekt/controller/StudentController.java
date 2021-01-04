package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.KorisnikDTO;
import progi.projekt.forms.RegisterForm;
import progi.projekt.model.Korisnik;
import progi.projekt.model.Student;
import progi.projekt.repository.StudentRepository;
import progi.projekt.service.StudentService;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("")
    public List<Student> listStudents() {
        return studentService.listAll();
    }

    public void init(){
        Student ivica = new Student();
        ivica.setEmail("kakaka@gma.com");
        ivica.setIme("Ivica");
        //studentRepository.save(ivica);
        System.out.println("Ivica umetnut");
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Validated RegisterForm registerForm) {
        Optional<Student> student = studentService.findByJmbag(registerForm.getJmbag());

        Student s = student.get();
        s.setIme(registerForm.getIme());
        s.setPrezime(registerForm.getPrezime());
        s.setEmail(registerForm.getEmail());
        s.setObavijestiNaMail(registerForm.isObavijestiNaMail());
        Student updatedStudent = studentService.update(s);

        KorisnikDTO korisnikDTO = new KorisnikDTO(updatedStudent);

        return ResponseEntity.ok(korisnikDTO);
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestBody @Validated RegisterForm registerForm) {

        Optional<Student> student = studentService.findByJmbag(registerForm.getJmbag());
        Student s = student.get();

        studentService.delete(s);
        return ResponseEntity.ok(null);
    }
}
