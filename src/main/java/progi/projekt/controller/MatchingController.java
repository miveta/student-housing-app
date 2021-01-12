package progi.projekt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.model.Par;
import progi.projekt.service.KandidatService;
import progi.projekt.service.MatchingService;

import java.util.List;

///Kontroler koji stvara i azurira listu kandidatnih soba/veza, listu valjanih parova i odgovarajuce oglase

@RestController
@RequestMapping("/match")
public class MatchingController {
	private final MatchingService matchingService;


	public MatchingController(MatchingService matchingService)
	{
		this.matchingService = matchingService;
	}


	@GetMapping("/")
	public String demo() {
		return "Matching Controller";
	}


	@GetMapping("/kandidatiRun")
	public ResponseEntity<?> kandidatiRun() {
		//prolazi po svim oglasima i stvara kandidat parove

		try {
			matchingService.kandidatiFun();
			return ResponseEntity.ok("Stvoreni kandidati za svaki oglas");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	//u medjuvremenu je student na stranici svog oglasa ocjenio ponudjene kandidate


	@GetMapping("/lajkRun")
	public ResponseEntity<?> lajkRun() {
		//poziva se uz metodu iz LajkControllera. Stvara kandidata i par ako je student ocjenio oglas koji mu inicijalno
		// nije ponudjen kao par pa nije mogao biti u topN pa niti postati par

		try {
			matchingService.lajkFun();
			return ResponseEntity.ok("Stvoreni dodatni parovi za dodatno ocjenjene kandidate");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	@GetMapping("/parRun")
	public ResponseEntity<?> parRun() {
		//prolazi po svim oglasima i puni tablicu 'par'

		try {
			matchingService.parFun();
			return ResponseEntity.ok("Stvoreni parovi oglasa, rangirano prema uvjetima studenata");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	@GetMapping("/matchRun")
	public ResponseEntity<?> matchRun() {
		//sece po tablici 'par' i ovisno o obostranoj ocjeni para "rezervira" najbolje ocjenjeni par

		try {
			matchingService.matchFun();
			return ResponseEntity.ok("Rezervirani parovi prema vremenu objave oglasa i ocjeni studenata");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	//u medjuvremenu su oba studenta prihvatila/odbila konacnu potvrdu


	@GetMapping("/confirmRun")
	public ResponseEntity<?> confirmRun() {
		//Cita rezultat konacnih potvrda para i:
		//ako su oba studenta prihvatila oznacuje par.done = true, oglas.status=POTVRDEN, postavlja ignore=true u sve
		// ostale parove i kandidate i salje mail u SC
		//ako oba studenta nisu prihvatila (par.ignore = true): oznacuje kandidat sa ignore (vise
		// se ne spajaju/gledaju)

		//tj, ako je lanac=true onda gledamo 3 oglasa, ne samo 2

		try {
			matchingService.confirmFun();
			return ResponseEntity.ok("Medjusobno potvrdjeni parovi oznaceni sa par.done=true i oglas.status=POTVRDEN");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}



	@GetMapping(value = "/confirmSCRun")
	public ResponseEntity<?> confirmSCRun() {
		//sve oglase ciji par.odobren=true oznacava kao IZVEDEN, a one sa par.dobren=false kao ODBIJEN

		try {
			matchingService.confirmSCFun();
			return ResponseEntity.ok("Potvrdjeni oglasi oznaceni kao IZVEDEN, ostali kao ODBIJEN");

		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
