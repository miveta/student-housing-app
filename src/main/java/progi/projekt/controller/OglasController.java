package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progi.projekt.dto.OglasDTO;
import progi.projekt.model.Oglas;
import progi.projekt.service.OglasService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/oglas")
public class OglasController {

    @Autowired
    private OglasService oglasService;

    @GetMapping("/list")
    public List<OglasDTO> listOglas() {
        List<Oglas> oglasi = oglasService.listAll();
        List<OglasDTO> oglasiDTO = oglasi.stream().map(o -> new OglasDTO(o)
        ).collect(Collectors.toList());

        return oglasiDTO;
    }
}