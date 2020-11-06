package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import progi.projekt.model.Student;
import progi.projekt.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    public List<Student> listStudents() {
        return studentService.listAll();
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }
}
