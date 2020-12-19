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
		//prolazi po svim oglasima i stvara kandidat parove

		/*
		//popunjavanje liste postojecih kandidata svakog oglasa
		//ili ce to Spring napraviti sam radi @OneToMany(fetch = FetchType.LAZY, mappedBy = "kandidati") u Oglas.java?
		foreach oglas in oglasi
			foreach kand in kandidat
				if kand.oglas1 ili kand.oglas2 == oglas
					oglas.kandidati.add(kandidat)

		//dodavanje novih kandidata
		foreach oglas in oglasi
			foreach kand in oglasi
				if (odgovaraju(oglas, kand) && josNisuKandidat(oglas, kand)){
					score = sobaN.evaluate => {
						uvijeti = oglas.trazeniUvijeti
						tezine = oglas.tezine
						rez = SUM(uvijeti * tezine)
						return rez
						}

					var kand = new Kandidat{
						id_kandidat = AUTOGEN
						id_oglas = oglas.id
						id_kind_oglasa = kand.id
						bliskost = score //sluzi sa sortirani ispis korisniku
						ignore = false
					}

					oglas.kandidati.add(kand)?
					ili
					ctx.save(kand)?
				}


		*/
		return ResponseEntity.ok("200");
	}

	//u medjuvremenu je student na stranici svog oglasa ocjenio ponudjene kandidate

	@GetMapping("/par")
	public ResponseEntity<?> par() {
		//prolazi po svim oglasima i puni tablicu 'par'

		/*


		foreach oglas in oglasi
			top3 = topN(oglas)

			//ako je nas oglas u listi top SHORTLIST_VELICINA oglasa nekog od nasih top SHORTLIST_VELICINA oglasa
			int i = odgovarajuTopN(oglas)
			if (i != -1){
				par = new Par {
					oglas1 = oglas
					oglas2 = oglas.getKandidati[i]
				}
				ctx.save(par)
				oglas.status = StatusOglasaEnum.CEKA
				immediateKandidat.status = StatusOglasaEnum.CEKA
			}

			//ako postoji lanac
			//todo
		*/
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
