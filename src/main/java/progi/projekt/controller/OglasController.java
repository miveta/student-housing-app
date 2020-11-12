package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progi.projekt.model.Oglas;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.OglasService;

import java.util.List;

@RestController
@RequestMapping("/oglas")
public class OglasController {

    @Autowired
    private OglasService oglasService;

    @GetMapping("")
    public List<Oglas> listOglas() {

        return oglasService.listAll();
    }


}
