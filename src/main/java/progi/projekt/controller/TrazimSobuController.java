package progi.projekt.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.DomDTO;
import progi.projekt.dto.UvjetiDTO;
import progi.projekt.forms.TrazimSobuForm;
import progi.projekt.model.*;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;
import progi.projekt.service.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/trazimSobu")
public class TrazimSobuController {

    private StudentService studentService;
    private TrazimSobuService trazimSobuService;
    private UtilService utilService;
    private SobaService sobaService;
    private OglasService oglasService;

    public TrazimSobuController(StudentService studentService, TrazimSobuService trazimSobuService, UtilService utilService, SobaService sobaService, OglasService oglasService) {
        this.studentService = studentService;
        this.trazimSobuService = trazimSobuService;
        this.utilService = utilService;
        this.sobaService = sobaService;
        this.oglasService = oglasService;
    }

    @GetMapping("/domovi")
    public Set<DomDTO> getDomovi(@RequestParam(value = "user") String username) {
        Set<Dom> domovi = trazimSobuService.findAllDom(username);
        return domovi.stream().map(DomDTO::new).sorted().collect(Collectors.toSet());

    }

    @GetMapping("/zadano")
    public UvjetiDTO zadano(@RequestParam(value = "user") String username){
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        Student student = optionalStudent.get();
        TrazeniUvjeti trazeniUvjeti = null;
        if (student.getAktivniOglas() != null) {
            trazeniUvjeti = student.getAktivniOglas().getTrazeniUvjeti();
        }
        if (trazeniUvjeti == null)
            trazeniUvjeti = new TrazeniUvjeti();
        return new UvjetiDTO(trazeniUvjeti);
    }

    @PostMapping(value = "/uvjetiIveta", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uvjeti(@RequestBody TrazimSobuForm trazimSobuForm) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(trazimSobuForm.getStudentUsername());

        if (optionalStudent.isEmpty()) return ResponseEntity.badRequest().build();
        Student student = optionalStudent.get();
        if (student.getSoba() == null) return ResponseEntity.badRequest().build();

        TrazeniUvjeti trazeniUvjeti = null;

        if (student.getAktivniOglas() != null) {
            trazeniUvjeti = student.getAktivniOglas().getTrazeniUvjeti();
        }

        if (trazeniUvjeti == null)
            trazeniUvjeti = new TrazeniUvjeti();


        trazeniUvjeti.setGrad(student.getGrad());


        Set<Integer> katovi = new HashSet<>(Arrays.asList(trazimSobuForm.getKatovi()));
        trazeniUvjeti.setKatovi(katovi);


        Set<Dom> domovi = new HashSet<>();
        for (String domId : trazimSobuForm.getDomId()) {
            Optional<Dom> optionalDom = utilService.getDomById(domId);
            if (optionalDom.isEmpty())
                return ResponseEntity.badRequest().build();
            domovi.add(optionalDom.get());
        }
        trazeniUvjeti.setDomovi(domovi);


        Set<Paviljon> paviljoni = new HashSet<>();
        for (String paviljonId : trazimSobuForm.getPaviljoni()) {
            Optional<Paviljon> optionalPaviljon = utilService.getPaviljonById(paviljonId);
            if (optionalPaviljon.isEmpty()) return ResponseEntity.badRequest().build();
            paviljoni.add(optionalPaviljon.get());
        }
        trazeniUvjeti.setPaviljoni(paviljoni);


        Set<BrojKrevetaEnum> brojKreveta = new HashSet<>(Arrays.asList(trazimSobuForm.getBrojKreveta()));
        trazeniUvjeti.setBrojKreveta(brojKreveta);

        Set<TipKupaoniceEnum> tipKupaonice = new HashSet<>(Arrays.asList(trazimSobuForm.getTipKupaonice()));
        trazeniUvjeti.setTipKupaonice(tipKupaonice);


        trazimSobuService.update(trazeniUvjeti);

        Optional<Oglas> optionalOglas = oglasService.findByStudentAndStatus(student, StatusOglasaEnum.AKTIVAN);
        if (optionalOglas.isEmpty()) {
            oglasService.spremiOglas(student, student.getSoba(), trazeniUvjeti);
        }

        return ResponseEntity.ok(trazimSobuForm);
    }
}
