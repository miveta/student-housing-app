package progi.projekt.controller;

import org.hibernate.LazyInitializationException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.ObavijestDTO;
import progi.projekt.model.Student;
import progi.projekt.service.ObavijestService;
import progi.projekt.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/obavijesti")
public class ObavijestController {
    private ObavijestService obavijestService;
    private StudentService studentService;

    public ObavijestController(ObavijestService obavijestService, StudentService studentService) {
        this.obavijestService = obavijestService;
        this.studentService = studentService;
    }

    @MessageMapping("/user-all")
    @SendTo("/topic/user")
    public List<ObavijestDTO> send(@Payload String studentUsername) {
        List<ObavijestDTO> obavijesti = new ArrayList<>();

        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(studentUsername);
        if (optionalStudent.isEmpty()) return obavijesti;
        Student student = optionalStudent.get();

        if (student.getObavijesti() == null || student.getObavijesti().isEmpty()) return obavijesti;
        try {
            return student.getObavijesti().stream().map(ObavijestDTO::new).collect(Collectors.toList());
        } catch (NullPointerException | LazyInitializationException ex) {
            return new ArrayList<>();
        }

    }

    @PostMapping(value = "/procitana")
    public boolean oznaciProcitana(@RequestParam(value = "id") String obavijestId) {
        return obavijestService.oznaciProcitana(UUID.fromString(obavijestId));
    }
}
