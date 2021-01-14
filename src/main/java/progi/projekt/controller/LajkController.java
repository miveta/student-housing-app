package progi.projekt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.service.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/lajk")
public class LajkController {

	private static final int MILISEC_IZMEDJU_POZIVA = 2 * 1000; //5 s

	private MatchingService matchingService;
	private LajkService lajkService;
	private StudentService studentService;
	private OglasService oglasService;
	private ObavijestService obavijestService;

	public LajkController(MatchingService matchingService, LajkService lajkService, StudentService studentService, OglasService oglasService, ObavijestService obavijestService) {
		this.matchingService = matchingService;
		this.lajkService = lajkService;
		this.studentService = studentService;
		this.oglasService = oglasService;
		this.obavijestService = obavijestService;
	}

	@GetMapping
	public List<Lajk> listLajks() {
		return lajkService.listAll();
	}


	@GetMapping(value = "/ocjena")
    public String ocjena(@RequestParam(value = "student_username") String studentUsername,
                         @RequestParam(value = "oglas_id") String oglasId) {
        Optional<Student> studentOpt = studentService.findByKorisnickoIme(studentUsername);
        Optional<Oglas> oglasOpt = oglasService.findById(oglasId);

        if (studentOpt.isEmpty() || oglasOpt.isEmpty()) return "";

		LajkId lajkId = new LajkId(studentOpt.get(), oglasOpt.get());
		Optional<Lajk> lajkOpt = lajkService.findLajk(lajkId); // zapravo nije bilo potrebno, i da ga nađe trenutno nam ne treba postojeća ocjena

		return lajkOpt.map(lajk -> String.valueOf(lajk.getOcjena())).orElse("");
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> update(@RequestParam(value = "student_username") String studentUsername,
									@RequestParam(value = "oglas_id") String oglasId,
									@RequestParam(value = "ocjena") int ocjena) {
		Optional<Student> studentOpt = studentService.findByKorisnickoIme(studentUsername);
		Optional<Oglas> oglasOpt = oglasService.findById(oglasId);

		LajkId lajkId = new LajkId(studentOpt.get(), oglasOpt.get());
		// Optional<Lajk> lajkOpt = lajkService.findLajk(lajkId); // zapravo nije bilo potrebno, i da ga nađe trenutno nam ne treba postojeća ocjena

		Lajk lajk = new Lajk();
		lajk.setLajkId(lajkId);
		lajk.setOcjena(ocjena);

		Lajk updatedLajk = lajkService.update(lajk);

		Thread kandidatiThread = new Thread(() -> {
			matchingService.lajkFun();
			try {
				Thread.sleep(MILISEC_IZMEDJU_POZIVA);
			} catch (InterruptedException e) {
				System.err.println("Scheduled matching execution interrupted");
			}
			//ne poziva se odmah nakon lajka da se korisnik ne matcha sa prvim oglasom kojeg lajka
			//matchingService.matchFun();
		});
		final boolean THREADS = false;
		if (THREADS){
			kandidatiThread.start();
		} else {
			matchingService.lajkFun();
			matchingService.kandidatiFun();
			matchingService.parFun();
			//ne poziva se odmah nakon lajka da se korisnik ne matcha sa prvim oglasom kojeg lajka
			//matchingService.matchFun();
		}

		return ResponseEntity.ok(updatedLajk);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestParam(value = "student_username") String studentUsername,
									@RequestParam(value = "oglas_id") String oglasId) {
		Optional<Student> studentOpt = studentService.findByKorisnickoIme(studentUsername);
		Optional<Oglas> oglasOpt = oglasService.findById(oglasId);

		LajkId lajkId = new LajkId(studentOpt.get(), oglasOpt.get());

		Lajk lajk = new Lajk();
		lajk.setLajkId(lajkId);

		Lajk deleted = lajkService.delete(lajk);

		return ResponseEntity.ok(deleted);
	}
}
