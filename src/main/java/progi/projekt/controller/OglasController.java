package progi.projekt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.KandidatDTO;
import progi.projekt.dto.OglasDTO;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Lajk;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.service.KandidatService;
import progi.projekt.service.LajkService;
import progi.projekt.service.OglasService;
import progi.projekt.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/oglas")
public class OglasController {


	private OglasService oglasService;
	private LajkService lajkService;
	private KandidatService kandidatService;
	private StudentService studentService;

	public OglasController(OglasService oglasService, LajkService lajkService, KandidatService kandidatService, StudentService studentService) {
		this.oglasService = oglasService;
		this.lajkService = lajkService;
		this.kandidatService = kandidatService;
		this.studentService = studentService;
	}

	@GetMapping("/list")
	public List<OglasDTO> listOglas() {
		return oglasService.listAll().stream().map(OglasDTO::new).collect(Collectors.toList());
	}

	@GetMapping("/getoglas")
	public ResponseEntity<?> getOglas(@RequestParam(value = "oglas_id") String oglasId) {
		Oglas oglas = oglasService.findById(oglasId).get();
		return ResponseEntity.ok(new OglasDTO(oglas));
	}

	@GetMapping(value = "/kandidati/student")
	public List<OglasDTO> kandidatiStudent(@RequestParam(value = "student_username") String username) {
		Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
		if (optionalStudent.isEmpty()) return null;

		Student student = optionalStudent.get();

		if (student.getOglas() == null) return null;

		List<Oglas> oglasKandidati = new ArrayList<>();
		student.getOglas().getKandidati().forEach(kandidat -> {
			if (kandidat.getOglas().getId().equals(student.getOglas().getId())) {
				oglasKandidati.add(kandidat.getKandOglas());
			} else oglasKandidati.add(kandidat.getOglas());
		});

		return oglasKandidati.stream().map(OglasDTO::new).collect(Collectors.toList());
	}

	@GetMapping(value = "/listKandidati")
	public ArrayList<KandidatDTO> listKandidati(@RequestParam(value = "oglas_id") String oglasId) {
		List<Oglas> oglasi = oglasService.listAll();

		//force update oglasa unutar svakog studenta
		List<Student> studenti = studentService.listAll();
		for (Oglas oglas : oglasi){
			for (Student stud : studenti){
				if (stud.getId() == oglas.getStudent().getId()){
					stud.setOglas(oglas);
					studentService.save(stud);
				}
			}
		}

		//force update kandidata unutar svakog oglasa
		kandidatService.updateLocalKands();


		Optional<Oglas> oglasOpt = oglasService.findById(oglasId.toString());
		ArrayList<KandidatDTO> kandidatiDTO = new ArrayList<>();

		if (oglasOpt.isPresent()) {
			Oglas oglas = oglasService.findById(oglasId).get();

			List<Lajk> lajkovi = lajkService.listAll();
			for (Lajk lajk : lajkovi) {
				if (lajk.getLajkId().getOglas().equals(oglas)) {
					Optional<Integer> ocjenaOptional = Optional.ofNullable(lajk.getOcjena());
					Oglas drugiOglas = lajk.getLajkId().getStudent().getOglas();

					Optional<Kandidat> tmpOpt = kandidatService.kandidatParaOglasa(oglas, drugiOglas);
					if (tmpOpt.isPresent()){
						Kandidat tmp = tmpOpt.get();
						KandidatDTO tmpDTO = new KandidatDTO(tmp);

						ocjenaOptional.ifPresentOrElse(
								(ocjena) ->
								{
									tmpDTO.setKandOcjena(ocjena);
								},
								() ->
								{
									//ako ocjena jos nije unesena upisujemo -1
									tmpDTO.setKandOcjena(-1);
								});

						kandidatiDTO.add(tmpDTO);
					}

				}
			}
		}


		return kandidatiDTO;
	}
}
