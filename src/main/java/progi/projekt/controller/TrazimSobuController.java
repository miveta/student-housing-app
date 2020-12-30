package progi.projekt.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import progi.projekt.dto.DomDTO;
import progi.projekt.dto.GradDTO;
import progi.projekt.dto.PaviljonDTO;
import progi.projekt.model.*;
import progi.projekt.service.StudentService;
import progi.projekt.service.TrazimSobuService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/trazimSobu")
public class TrazimSobuController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TrazimSobuService trazimSobuService;

    @GetMapping("/grad")
    public GradDTO getGrad(@RequestParam(value = "user") String username) {
        GradDTO grad = new GradDTO(trazimSobuService.findGrad(username));
        return grad;
    }

    @GetMapping("/domovi")
    public Set<DomDTO> getDomovi() {
        Set<Dom> domovi = trazimSobuService.findAllDom();
        return domovi.stream().map(DomDTO::new).collect(Collectors.toSet());

    }

    @GetMapping("/paviljoni")
    public Set<PaviljonDTO> getPavljoni() {
        Set<Paviljon> paviljoni = trazimSobuService.findAllPaviljon();
        return paviljoni.stream().map(PaviljonDTO::new).collect(Collectors.toSet());

    }

    @PutMapping(value = "/uvjeti", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody  TrazeniUvjeti trazeniUvjeti,
                                    @RequestParam(value = "user") String username) {
        Optional<Student> student = studentService.findBykorisnickoIme(username);

        TrazeniUvjeti uvjeti = student.get().getUvjeti();

        uvjeti.setBrojKreveta(trazeniUvjeti.getBrojKreveta());
        uvjeti.setTraziStudent(student.get());
        uvjeti.setKomentar(trazeniUvjeti.getKomentar());
        uvjeti.setTipKupaonice(trazeniUvjeti.getTipKupaonice());
        uvjeti.setDom(trazeniUvjeti.getDom());
        uvjeti.setKat(trazeniUvjeti.getKat());
        uvjeti.setPaviljon(trazeniUvjeti.getPaviljon());
        uvjeti.setGrad(trazeniUvjeti.getGrad());

        trazimSobuService.update(uvjeti);
        return ResponseEntity.ok(uvjeti);
    }


}
