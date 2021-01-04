package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progi.projekt.dto.GradDTO;
import progi.projekt.model.Grad;
import progi.projekt.service.SobaService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/soba")
public class SobaController {
    @Autowired
    SobaService sobaService;

    @GetMapping("/gradovi")
    public List<GradDTO> getSviGradovi() {
        List<Grad> gradovi = sobaService.findAllGrad();
        return gradovi.stream().map(GradDTO::new).collect(Collectors.toList());
    }
}
