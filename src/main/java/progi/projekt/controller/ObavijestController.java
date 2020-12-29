package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progi.projekt.model.Obavijest;
import progi.projekt.model.Student;
import progi.projekt.service.StudentService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/obavijesti")
public class ObavijestController {
    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/{username}")
    public List<Obavijest> getObavijesti(@PathVariable String username) {
        return studentService.getObavijest(username);
    }
}
