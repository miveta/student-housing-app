package progi.projekt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.model.Par;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.service.*;
import java.util.*;
//import java.awt.*;

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

	//sva ova "logika" ide u funkcije koje kontroler samo poziva i vraca t/f (200/500) oviso o njihovoj povratnoj info

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

	@GetMapping("/lajk")
	public ResponseEntity<?> lajk() {
		//poziva se uz metodu iz LajkControllera. Stvara kandidata/par ako je student ocjenio oglas koji mu inicijalno
		// nije ponudjen kao par pa nije mogao biti u topN pa niti postati par
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("400");
	}

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
			}

			//ako postoji lanac
			kandA = oglas //kandA je nas lanac
			List<Oglas> top3 = topN(oglas) //ako neki od kand nasih top3 zeli nas, imamo lanac
			for (Oglas kandB : top3){ //kandB su nasi kandidati
				List<Oglas> top3razina1 = topN(kandB)
				for (Oglas kandC : top3razina1) //kandC su kandidati nasih kandidata
					if (odgovara (kandC, kandA) { ako nekom kandidatu naseg kandidata odgovaramo mi, imamo lanac
						//par A->B, lanac=true
						//par B->C, lanac=true
						//par C->A, lanac=true
					}
			}

		*/
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("400");
	}

	@GetMapping("/match")
	public ResponseEntity<?> match() {
		//po tablici 'par' i ovisno o obostranoj ocjeni para "rezervira" najbolje ocjenjeni par

		/*

		//kopiranje ocjene iz liste lajkova u 2D polje lajkovi.length*lajkovi.length
		Array<Integer>[lajkovi.length][lajkovi.length] ocjene = new Collections.emptyArray();
		//bolje je napraviti dict[oglas1ID][oglas2ID] = ocjena

		for (Oglas oglas : oglasi){
			//iteriramo po oglasima a ne lajkovima kako bi polje ocjena bilo sortirano po vremenu stvaranj oglasa
			//tj tko prije stvori oglas taj ima vecu prednost u matchanju
			//zato je naravno predpostavka da je polje oglasi sortirano po datumu predaje

			//sada iteriramo po svim lajkovima tog studenta i pospremamo ih u polje
			//
			uuid studentID = oglas.getStudent().getId();
			for (Lajk lajk : lajk){
				if (lajk.getStudent() == studentID) {
					ocjene[oglasID][lajk.getOglasID()] = lajk.getOcjena();
				}
			}
		}

		//racuanje obostranih ocejena i pospremanje u rijecnik
		Dict<Par, Integer> popisObostranihOcjena;
		for (Par par : parovi){
			Integer ocjena1 = ocjene[par.getOglas1().getId()][par.getOglas2().getId()]
			Integer ocjena2 = ocjene[par.getOglas2().getId()][par.getOglas1().getId()]

			if (ocjena1 != null && ocjena2 != null) { //ako su oba studenta ocjenila par
				Integer obostranaOcjena = ocjena1 + ocjena2;
				popisObostranihOcjena[par] = obostranaOcjena
			}
		}


		//grupiranje oglasa u konacne parove i postavljanje status u CEKA
		for (Oglas oglas : oglasi){

			Par konacniPar = parovi
				.stream()
				.filter(par -> parService.sadrzi(par, oglas)) //par.oglas1 == oglas || par.oglas2 == oglas
				.filter(par -> parService.oglasiSuAKTIVAN(par) //t ako su stanja oba oglasa == AKTIVAN
				.sorted(Comparator.comparing(par -> popisObostranihOcjena[par])) //ili funk ako ovo ne radi
				.singleOrDefault()?
				ili
				.limit(1)
				.collect(Collectors.toObject())?;

			if (konacniPar != null){ //ako je pronadjen konacni kandidat
				//ovo mozda treba biti ref?
				Par par = parovi.find(konacniPar) //par1 = par2 ako oglasi para1 == oglasi para2
				par.getOglas1().setStanje(StatusOglasaEnum.CEKA)
				par.getOglas2().setStanje(StatusOglasaEnum.CEKA)
				par.getOglas1().setObavijest(par)
				par.getOglas2().setObavijest(par)
			}


		}




		*/
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("400");
	}

	//u medjuvremenu su oba studenta prihvatila/odbila konacnu potvrdu

	@GetMapping("/confirm")
	public ResponseEntity<?> confirm() {
		//Cita rezultat konacnih potvrda para i:
		//ako su oba studenta prihvatila: oznacuje par.done = true, oglas.status=POTVRDEN i salje mail u SC
		//ako oba studenta nisu prihvatila: oznacuje oba entiteta kandidat i par sa ignore (vise se ne spajaju/gledaju)

		//tj, ako je lanac=true onda gledamo 3 oglasa, ne samo 2

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("400");
	}


	@PostMapping(value = "/confirmSC", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> confirmSC(@RequestBody List<Par> izvedeni) {
		//Prolazi po predanoj listi 'izvedeni' i oznacava oglase nevednih parova kao IZVEDEN

		try {
			for(Par par : izvedeni){
				var oglas1 = par.getOglas1();
				oglas1.setStatus(StatusOglasaEnum.IZVEDEN);

				var oglas2 = par.getOglas2();
				oglas2.setStatus(StatusOglasaEnum.IZVEDEN);
			}

			return ResponseEntity.ok("Navedeni oglasi oznaceni kao IZVEDENI");

		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


}
