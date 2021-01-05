package progi.projekt.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import progi.projekt.dto.DomDTO;
import progi.projekt.dto.GradDTO;
import progi.projekt.dto.PaviljonDTO;
import progi.projekt.model.*;
import progi.projekt.repository.*;
import progi.projekt.service.StudentService;
import progi.projekt.service.TrazimSobuService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/trazimSobu")
public class TrazimSobuController {

    @Autowired
    DomRepository domRepository;

    @Autowired
    PaviljonRepository paviljonRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    BrojKrevetaRepository brojKrevetaRepository;

    @Autowired
    TipKupaoniceRepository tipKupaoniceRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private TrazimSobuService trazimSobuService;

    @GetMapping("/grad")
    public GradDTO getGrad(@RequestParam(value = "user") String username) {
        GradDTO grad = new GradDTO(trazimSobuService.findGrad(username));
        return grad;
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

    @PostMapping(value = "/uvjeti", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> put(@RequestParam(value = "user") String username,
                                    @RequestParam(value = "domovi") String[] domId,
                                    @RequestParam(value = "paviljoni") String[] paviljoni,
                                    @RequestParam(value = "katovi") String[] katovi,
                                    @RequestParam(value = "brojKreveta") String[] brojKreveta,
                                    @RequestParam(value = "tipKupaonice") String[] tipKupaonice,
                                    @RequestParam(value = "komentar") String komentar) {
        Student student = studentRepository.findByKorisnickoIme(username);
        Set<Dom> dom = new HashSet<>();
        TrazeniUvjeti uvjeti = student.getUvjeti();
        if(uvjeti == null){
            uvjeti = new TrazeniUvjeti();
        }
        uvjeti.setGodina(Calendar.getInstance().get(Calendar.YEAR));
        uvjeti.setKomentar(komentar);
        uvjeti.setTraziStudent(student);
        uvjeti.setGrad(student.getGrad());
        Set<Integer> kat = new HashSet<>();
        for(String k : katovi){
            kat.add(Integer.parseInt(k));
        }
        uvjeti.setKatovi(kat);
            for (String id : domId) {
                Dom d = domRepository.findById(UUID.fromString(id));
                dom.add(d);
            }
            uvjeti.setDomovi(dom);
        Set<Paviljon> paviljon = new HashSet<>();
        for (String id : paviljoni) {
                Paviljon d = paviljonRepository.findById(UUID.fromString(id));
                paviljon.add(d);
            }
            uvjeti.setPaviljoni(paviljon);

            Set<BrojKreveta> brKreveta = new HashSet<>();
            for (String b : brojKreveta) {
                BrojKreveta temp = brojKrevetaRepository.findByNaziv(b);
                brKreveta.add(temp);
            }
            uvjeti.setBrojKreveta(brKreveta);


            Set<TipKupaonice> tKupaonice = new HashSet<>();
            for (String t : tipKupaonice) {
                TipKupaonice temp = tipKupaoniceRepository.findByTip(t);
                tKupaonice.add(temp);
            }
            uvjeti.setTipKupaonice(tKupaonice);

        trazimSobuService.update(uvjeti);
        return ResponseEntity.ok("uvjeti uneseni");

    }



}