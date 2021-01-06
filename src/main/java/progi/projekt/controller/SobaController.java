package progi.projekt.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.GradDTO;
import progi.projekt.dto.SobaDTO;
import progi.projekt.forms.SobaForm;
import progi.projekt.model.Grad;
import progi.projekt.model.Soba;
import progi.projekt.service.SobaService;
import progi.projekt.service.StudentService;
import progi.projekt.service.UtilService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/soba")
public class SobaController {

    private SobaService sobaService;
    private UtilService utilService;
    private StudentService studentService;

    public SobaController(SobaService sobaService, UtilService utilService, StudentService studentService) {
        this.sobaService = sobaService;
        this.utilService = utilService;
        this.studentService = studentService;
    }


    @GetMapping("/gradovi")
    public List<GradDTO> getSviGradovi() {
        List<Grad> gradovi = sobaService.findAllGrad();
        return gradovi.stream().map(GradDTO::new).collect(Collectors.toList());
    }


    @GetMapping("/student")
    public SobaDTO getStudentSoba(@RequestParam(value = "student_username") String studentUsername) {
        Optional<Soba> optionalSoba = sobaService.getByStudentUsername(studentUsername);

        if (optionalSoba.isPresent()) return new SobaDTO(optionalSoba.get());
        else return null;
    }

    @PostMapping(value = "/spremi", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveSoba(@RequestBody SobaForm sobaForm) {
        Optional<Soba> optionalSoba = sobaService.getByStudentUsername(sobaForm.getStudentUsername());

        Soba soba;
        if (optionalSoba.isEmpty()) {
            soba = new Soba();
            sobaForm.fromSobaForm(soba);
            optionalSoba = sobaService.setFromStudentUsernameAndPaviljonId(soba, sobaForm.getStudentUsername(), sobaForm.getIdPaviljon());

            if (optionalSoba.isEmpty()) return ResponseEntity.badRequest().build();
        } else {
            soba = optionalSoba.get();
            sobaForm.fromSobaForm(soba);
        }

        soba = sobaService.save(soba);
        return ResponseEntity.ok(new SobaDTO(soba));
    }
}
