package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progi.projekt.model.Student;
import progi.projekt.repository.StudentRepository;
import progi.projekt.service.StudentService;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;
import java.util.List;

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
}
