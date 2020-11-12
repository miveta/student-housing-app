package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.OglasDTO;
import progi.projekt.model.Oglas;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.OglasService;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/oglas")
public class OglasController {

    @Autowired
    private OglasService oglasService;

    @GetMapping("")
    public List<OglasDTO> listOglas() {
        List<Oglas> oglasi = oglasService.listAll();
        List<OglasDTO> oglasiDTO = oglasi.stream().map(o -> new OglasDTO(o)
        ).collect(Collectors.toList());

        return oglasiDTO;
    }
}