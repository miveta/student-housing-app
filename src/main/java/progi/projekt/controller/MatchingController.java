package progi.projekt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.service.*;

///Kontroler koji stvara, azurira i servira listu kandidatnih soba/veza

@RestController
@RequestMapping("/match")
public class MatchingController {
	private final StudentService studentService;
	private final OglasService oglasService;
	private final KandidatService kandidatService;
	private final ParService parService;


	public MatchingController
			(
			StudentService studentService,
			OglasService oglasService,
			KandidatService kandidatService,
			ParService parService
			)
	{
		this.studentService = studentService;
		this.oglasService = oglasService;
		this.kandidatService = kandidatService;
		this.parService = parService;
	}


	@GetMapping("/")
	public String demo() {
		return "MatchingController";
	}

	@GetMapping("/kandidati")
	public ResponseEntity<?> kandidati() {
		//prolazi po svim oglasima i puni listu kandidata
		return ResponseEntity.ok("200");
	}

	@GetMapping("/par")
	public ResponseEntity<?> par() {
		//prolazi po svim oglasima i puni tablicu 'par'
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("400");
	}

	@GetMapping("/match")
	public ResponseEntity<?> match() {
		//po tablici 'par' i modificira ju ovisno o povratnoj informaciji kandidata
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("400");
	}

	/*
	@RequestMapping({"/kandidati"})
	public String firstPage() {
		return "Ovo ce vracati JSON listu kandidatnih soba";
	}
	*/

}
