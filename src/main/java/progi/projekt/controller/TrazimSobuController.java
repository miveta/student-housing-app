package progi.projekt.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.DomDTO;
import progi.projekt.dto.GradDTO;
import progi.projekt.forms.TrazimSobuForm;
import progi.projekt.model.*;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;
import progi.projekt.service.SobaService;
import progi.projekt.service.StudentService;
import progi.projekt.service.TrazimSobuService;
import progi.projekt.service.UtilService;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/trazimSobu")
public class TrazimSobuController {

    private StudentService studentService;

    private TrazimSobuService trazimSobuService;

    private UtilService utilService;

    private SobaService sobaService;

    public TrazimSobuController(StudentService studentService, TrazimSobuService trazimSobuService, UtilService utilService, SobaService sobaService) {
        this.studentService = studentService;
        this.trazimSobuService = trazimSobuService;
        this.utilService = utilService;
        this.sobaService = sobaService;
    }

    @GetMapping("/grad")
    public GradDTO getGrad(@RequestParam(value = "user") String username) {
        List<Grad> list = sobaService.findAllGrad();
        return new GradDTO(list.get(0));
    }

    @GetMapping("/domovi")
    public Set<DomDTO> getDomovi(@RequestParam(value = "user") String username) {
        Set<Dom> domovi = trazimSobuService.findAllDom(username);
        return domovi.stream().map(DomDTO::new).collect(Collectors.toSet());

    }

//    @GetMapping("/paviljoni")
//    public Set<PaviljonDTO> getPavljoni() {
//        List<Paviljon> paviljoni = trazimSobuService.findAllPaviljon();
//        return paviljoni.stream().map(PaviljonDTO::new).collect(Collectors.toSet());
//
//    }

    @PostMapping(value = "/uvjetiIveta", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uvjeti(@RequestBody TrazimSobuForm trazimSobuForm) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(trazimSobuForm.getStudentUsername());

        if (optionalStudent.isEmpty()) return ResponseEntity.badRequest().build();
        Student student = optionalStudent.get();


        TrazeniUvjeti trazeniUvjeti = student.getUvjeti();

        if (trazeniUvjeti == null)
            trazeniUvjeti = new TrazeniUvjeti();


        trazeniUvjeti.setTraziStudent(student);
        trazeniUvjeti.setGrad(student.getGrad());
        trazeniUvjeti.setGodina(Calendar.getInstance().get(Calendar.YEAR));


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

        return ResponseEntity.ok().build();
    }

}
