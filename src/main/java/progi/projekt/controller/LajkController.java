package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progi.projekt.model.Lajk;
import progi.projekt.service.LajkService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/lajk")
public class LajkController {

    @Autowired
    private LajkService lajkService;

    @GetMapping
    public List<Lajk> listLajks() {
        return lajkService.listAll();
    }

}
