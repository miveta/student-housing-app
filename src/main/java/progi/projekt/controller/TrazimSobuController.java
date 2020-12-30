package progi.projekt.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public GradDTO getGrad(@PathVariable String username) {
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




}
