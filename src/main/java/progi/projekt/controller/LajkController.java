package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.service.LajkService;
import progi.projekt.service.OglasService;
import progi.projekt.service.StudentService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/lajk")
public class LajkController {

	@Autowired
	private LajkService lajkService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private OglasService oglasService;

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

		if (lajkOpt.isPresent()) {
			return String.valueOf(lajkOpt.get().getOcjena());
		} else {
			return "";
		}
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
