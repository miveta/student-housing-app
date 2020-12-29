package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import progi.projekt.model.Obavijest;
import progi.projekt.service.ObavijestService;
import progi.projekt.service.StudentService;

import java.util.List;

public class ObavijestServiceImpl implements ObavijestService {
    @Autowired
    private StudentService studentService;

}
