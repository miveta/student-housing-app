package progi.projekt.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.GradDTO;
import progi.projekt.dto.SobaDTO;
import progi.projekt.forms.SobaForm;
import progi.projekt.model.*;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.service.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/soba")
public class SobaController {
    private SobaService sobaService;
    private UtilService utilService;
    private OglasService oglasService;
    private StudentService studentService;
    private final TrazimSobuService trazimSobuService;
    private MatchingService matchingService;

    public SobaController(SobaService sobaService, UtilService utilService, OglasService oglasService, StudentService studentService, TrazimSobuService trazimSobuService, MatchingService matchingService) {
        this.sobaService = sobaService;
        this.utilService = utilService;
        this.oglasService = oglasService;
        this.studentService = studentService;
        this.trazimSobuService = trazimSobuService;
        this.matchingService = matchingService;
    }

    @GetMapping("/gradovi")
    public List<GradDTO> getSviGradovi() {
        List<Grad> gradovi = utilService.findAllGrad();
        return gradovi.stream().map(GradDTO::new).sorted().collect(Collectors.toList());
    }


    @GetMapping("/student")
    public ResponseEntity<?> getStudentSoba(@RequestParam(value = "student_username") String studentUsername) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(studentUsername);
        if (optionalStudent.isEmpty())
            return ResponseEntity.badRequest().body("Student s tim korisničkim imenom ne postoji!");
        Student student = optionalStudent.get();

        Oglas aktivniOglas = student.getAktivniOglas();
        if (aktivniOglas == null)
            return ResponseEntity.ok(null);

        else return ResponseEntity.ok(new SobaDTO(aktivniOglas.getSoba()));
    }

    @PostMapping(value = "/spremi", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveSoba(@RequestBody SobaForm sobaForm) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(sobaForm.getStudentUsername());
        if(optionalStudent.isEmpty()) return ResponseEntity.badRequest().build();

        Student student = optionalStudent.get();
        Oglas oglas = student.getAktivniOglas();


        Soba soba;
        if (oglas == null) {
            soba = new Soba();
            soba = sobaService.setFromStudentUsernameAndPaviljonId(soba, sobaForm.getStudentUsername(), sobaForm.getIdPaviljon());

            if (soba == null) return ResponseEntity.badRequest().build();
        } else {
            soba = oglas.getSoba();
        }

        sobaForm.fromSobaForm(soba);
        soba = sobaService.save(soba);

        String paviljonId = sobaForm.getIdPaviljon();
        if (paviljonId == null) return ResponseEntity.badRequest().build();
        if (!soba.getPaviljon().getId().equals(UUID.fromString(paviljonId))) sobaService.setPaviljon(soba, paviljonId);


        student.setSoba(soba);
        studentService.update(student);

        Optional<Oglas> optionalOglas = oglasService.findByStudentAndStatus(student, StatusOglasaEnum.AKTIVAN);
        optionalOglas.ifPresentOrElse(
                (o) ->
                {
                    matchingService.resetirajOglas(o.getId());
                },
                () ->
                //if (optionalOglas.isEmpty()) {
                {

                    TrazeniUvjeti trazeniUvjeti = new TrazeniUvjeti();
                    trazeniUvjeti.setGrad(student.getGrad());
                    trazimSobuService.update(trazeniUvjeti);
                    oglasService.spremiOglas(student, student.getSoba(), trazeniUvjeti);
                    matchingService.kandidatiFun();
                });

        return ResponseEntity.ok(new SobaDTO(soba));
    }

    @GetMapping(value = "/getsoba")
    public ResponseEntity<?> getSoba(@RequestParam(value = "oglas_id") String oglasId) {
        Student student = oglasService.findById(oglasId).get().getStudent();
        return ResponseEntity.ok(new SobaDTO(student.getSoba()));
    }
}
