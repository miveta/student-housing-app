package progi.projekt.controller;

import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.GradDTO;
import progi.projekt.model.Grad;
import progi.projekt.model.Soba;
import progi.projekt.service.SobaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/soba")
public class SobaController {

    private SobaService sobaService;

    public SobaController(SobaService sobaService) {
        this.sobaService = sobaService;
    }


    @GetMapping("/gradovi")
    public List<GradDTO> getSviGradovi() {
        List<Grad> gradovi = sobaService.findAllGrad();
        return gradovi.stream().map(GradDTO::new).collect(Collectors.toList());
    }


    @GetMapping("/student")
    public Soba getStudentSoba(@RequestParam(value = "student_username") String studentUsername) {
        Optional<Soba> optionalSoba = sobaService.getByStudentUsername(studentUsername);

        if (!optionalSoba.isEmpty()) return optionalSoba.get();
        else return null;
    }

}
