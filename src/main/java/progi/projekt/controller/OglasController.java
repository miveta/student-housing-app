package progi.projekt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.KandidatDTO;
import progi.projekt.dto.OglasDTO;
import progi.projekt.dto.ParDTO;
import progi.projekt.model.*;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/oglas")
public class OglasController {


    private OglasService oglasService;
    private LajkService lajkService;
    private KandidatService kandidatService;
    private StudentService studentService;
    private ParService parService;
    private ZaposlenikSCService zaposlenikSCService;
    private MatchingService matchingService;

    public OglasController(OglasService oglasService, LajkService lajkService, KandidatService kandidatService, StudentService studentService, ParService parService, ZaposlenikSCService zaposlenikSCService, MatchingService matchingService) {
        this.oglasService = oglasService;
        this.lajkService = lajkService;
        this.kandidatService = kandidatService;
        this.studentService = studentService;
        this.parService = parService;
        this.zaposlenikSCService = zaposlenikSCService;
        this.matchingService = matchingService;
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

    @GetMapping("/getparovi")
    public ResponseEntity<?> getOglasParovi(@RequestParam(value = "oglas_id") String oglasId) {
        Oglas oglas = oglasService.findById(oglasId).get();
        return ResponseEntity.ok(new OglasDTO(oglas));
    }

    @GetMapping(value = "/arhivirani")
    public List<OglasDTO> arhivirani(@RequestParam(value = "student_username") String username) {
        return studentService.oglasi(username, StatusOglasaEnum.ARHIVIRAN).stream().map(OglasDTO::new).collect(Collectors.toList());
    }

    @PostMapping(value = "/arhiviraj")
    public ResponseEntity<?> arhiviraj(@RequestParam(value = "student_username") String username) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        if (optionalStudent.isEmpty())
            return ResponseEntity.badRequest().body("Student s tim korisničkim imenom ne postoji!");
        Student student = optionalStudent.get();

        Oglas aktivniOglas = student.getAktivniOglas();
        if (aktivniOglas == null)
            return ResponseEntity.badRequest().body("Student s tim korisnilkim imenom nema aktivan oglas!");


        student.setSoba(null);
        studentService.save(student);

        aktivniOglas.setStatusOglasa(StatusOglasaEnum.ARHIVIRAN);
        oglasService.save(aktivniOglas);

        return ResponseEntity.ok(new OglasDTO(aktivniOglas));
    }

    @PostMapping(value = "/aktiviraj")
    public ResponseEntity<?> aktiviraj(@RequestParam(value = "student_username") String username, @RequestParam(value = "oglas_id") String oglasId) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        if (optionalStudent.isEmpty())
            return ResponseEntity.badRequest().body("Student s tim korisničkim imenom ne postoji!");
        Student student = optionalStudent.get();

        Oglas aktivniOglas = student.getAktivniOglas();
        if (aktivniOglas != null)
            return ResponseEntity.badRequest().body("Student s tim korisnilkim imenom već ima aktivan oglas!");

        Optional<Oglas> optionalArhiviraniOglas = oglasService.findById(oglasId);
        if (optionalArhiviraniOglas.isEmpty())
            return ResponseEntity.badRequest().body("Oglas koji se pokušava aktivirati ne postoji!");

        Oglas arhiviraniOglas = optionalArhiviraniOglas.get();


        student.setSoba(arhiviraniOglas.getSoba());
        studentService.save(student);

        arhiviraniOglas.setStatusOglasa(StatusOglasaEnum.AKTIVAN);
        oglasService.save(arhiviraniOglas);
        return ResponseEntity.ok(new OglasDTO(arhiviraniOglas));
    }

    @GetMapping(value = "/kandidati/student")
    public List<OglasDTO> kandidatiStudent(@RequestParam(value = "student_username") String username) {
        kandidatService.updateLocalKands();

        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        if (optionalStudent.isEmpty()) return null;

        Student student = optionalStudent.get();

        if (student.getAktivniOglas() == null) return null;

        List<Oglas> oglasKandidati = new ArrayList<>();
        student.getAktivniOglas().getKandidati().forEach(kandidat -> {
            if (kandidat.getOglas().getId().equals(student.getAktivniOglas().getId())) {
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
        for (Oglas oglas : oglasi) {
            for (Student stud : studenti) {
                if (stud.getId() == oglas.getStudent().getId()) {
                    stud.setAktivniOglas(oglas);
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
                    Oglas drugiOglas = lajk.getLajkId().getStudent().getAktivniOglas();

                    Optional<Kandidat> tmpOpt = kandidatService.kandidatParaOglasa(oglas, drugiOglas);
                    if (tmpOpt.isPresent()) {
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


    @GetMapping(value = "/listParovi")
    public List<ParDTO> listParovi(@RequestParam(value = "student_username") String username) {
        //note: napravio sam ParDTO jer ako stavim Par u listu, toString je beskonacan jer oglas ima referencu na
        // studenta koji opet ima referencu na oglas. Ista stvar sa domovima
        // - holik

        //force update kandidata unutar svakog oglasa
        kandidatService.updateLocalKands();

        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        if (optionalStudent.isEmpty()) return new ArrayList<>();

        Student student = optionalStudent.get();
        if (student.getAktivniOglas() == null) return new ArrayList<>();


        Oglas oglas = student.getAktivniOglas();

        Optional<Par> parOpt = parService.pripadniParOglasa(oglas);
        if (parOpt.isPresent()) {
            Par par = parOpt.get();

            ArrayList<ParDTO> paroviOglasa = new ArrayList<>();

            if (par.getLanac() == false) {
                //nije lanac
                ParDTO parDTO = new ParDTO(par);
                paroviOglasa.add(parDTO);
            } else {
                //je lanac
                List<Par> lanac = parService.pripadniParoviLanca(par.getOglas1());
                for (Par parLanca : lanac) {
                    ParDTO parDTO = new ParDTO(parLanca);
                    paroviOglasa.add(parDTO);
                }
            }
            return paroviOglasa;
        }

        return new ArrayList<>();
    }


    /*@GetMapping(value = "/listParoviWithFlags")
    public List<ParDTO> listParoviWithFlags(@RequestParam Boolean ignore, Boolean done, Boolean odobren) {
        //note: napravio sam ParDTO jer ako stavim Par u listu, toString je beskonacan jer oglas ima referencu na
        // studenta koji opet ima referencu na oglas. Ista stvar sa domovima
        // - holik

        List<Oglas> oglasi = oglasService.listAll();

        //force update oglasa unutar svakog studenta
        List<Student> studenti = studentService.listAll();
        for (Oglas oglas : oglasi) {
            for (Student stud : studenti) {
                if (stud.getId() == oglas.getStudent().getId()) {
                    stud.setAktivniOglas(oglas);
                    studentService.save(stud);
                }
            }
        }

        //force update kandidata unutar svakog oglasa
        kandidatService.updateLocalKands();

        ArrayList<ParDTO> parovi = new ArrayList<>();

        for (Par par : parService.listAll()) {
            if (par.getIgnore() == ignore && par.getDone() == done && par.getOdobren() == odobren) {
                ParDTO parDTO = new ParDTO(par);
                parovi.add(parDTO);
            }
        }

        return parovi;
    }*/

    @GetMapping(value = "/listParoviWithFlags")
    public ResponseEntity<?>/*List<ParDTO>*/ listParoviWithFlags(/*@RequestParam(value = "ignore") Boolean ignore,
											@RequestParam(value = "done") Boolean done,
											@RequestParam(value = "odobren") Boolean odobren*/) {
        //note: napravio sam ParDTO jer ako stavim Par u listu, toString je beskonacan jer oglas ima referencu na
        // studenta koji opet ima referencu na oglas. Ista stvar sa domovima
        // - holik

		/*List<Oglas> oglasi = oglasService.listAll();

		//force update oglasa unutar svakog studenta
		List<Student> studenti = studentService.listAll();
		for (Oglas oglas : oglasi){
			for (Student stud : studenti){
				if (stud.getId() == oglas.getStudent().getId()){
					stud.setAktivniOglas(oglas);
					studentService.save(stud);
				}
			}
		}

		//force update kandidata unutar svakog oglasa
		kandidatService.updateLocalKands();

		ArrayList<ParDTO> parovi = new ArrayList<>();

		for (Par par : parService.listAll()){
				if (par.getIgnore() == ignore && par.getDone() == done && par.getOdobren() == odobren){
					ParDTO parDTO = new ParDTO(par);
					parovi.add(parDTO);
				}
		}*/

        List<ParDTO> parovi = new ArrayList<>();
        List<OglasDTO> oglasi = oglasService.listAll().stream().map(OglasDTO::new).collect(Collectors.toList());
        OglasDTO oglasdto1 = oglasi.get(0);
        Oglas oglas1 = oglasService.findById(oglasdto1.getId().toString()).get();
        OglasDTO oglasdto2 =  oglasi.get(1);
        Oglas oglas2 = oglasService.findById(oglasdto2.getId().toString()).get();

        parovi.add(new ParDTO(new Par(oglas1, oglas2, true,false, false)));
        return ResponseEntity.ok(parovi);
    }


/*    @PostMapping(value = "/updateParSC", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateParSC(@RequestParam(value = "par_id") String parId,
                                         @RequestParam(value = "odobren") Boolean odobren,
                                         @RequestParam(value = "zaposlenikKorisnickoIme") String username) {
        ZaposlenikSC zaposlenikSC = zaposlenikSCService.findBykorisnickoIme(username).get();


        return ResponseEntity.ok(null);
    }*/

    @PostMapping("/updatePar")
    public ResponseEntity<?> updatePar(@RequestParam(value = "par_id") String parId,
                                       @RequestParam(value = "odobren") Boolean odobren,
                                       @RequestParam(value = "student_username") String username) {

        kandidatService.updateLocalKands();

        Optional<Par> optionalPar = parService.find(Long.parseLong(parId));
        if (optionalPar.isEmpty()) return ResponseEntity.badRequest().body("Ne postoji par s tim id-em!");

        Par par = optionalPar.get();

        boolean prvi = par.getOglas1().getStudent().getKorisnickoIme().equals(username);
        boolean drugi = par.getOglas2().getStudent().getKorisnickoIme().equals(username);

        if (prvi && drugi || !prvi && !drugi) return ResponseEntity.badRequest().body("!");

        if (odobren == false) par.setIgnore(true);
        else {
            if (prvi) par.setPrihvatioPrvi(true);
            if (drugi) par.setPrihvatioDrugi(true);

            if (par.getPrihvatioPrvi() != null && par.getPrihvatioDrugi() != null)
                par.setDone(par.getPrihvatioPrvi() && par.getPrihvatioDrugi());
        }

        parService.update(par);
        matchingService.confirmFun();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/updateParSC")
    public ResponseEntity<?> updateParSc(@RequestParam(value = "par_id") String parId,
                                         @RequestParam(value = "odobren") Boolean odobren,
                                         @RequestParam(value = "zaposlenikKorisnickoIme") String username) {

/*        List<Oglas> oglasi = oglasService.listAll();

        //force update oglasa unutar svakog studenta
        List<Student> studenti = studentService.listAll();
        for (Oglas oglas : oglasi) {
            for (Student stud : studenti) {
                if (stud.getId() == oglas.getStudent().getId()) {
                    stud.setAktivniOglas(oglas);
                    studentService.save(stud);
                }
            }
        }*/

        //force update kandidata unutar svakog oglasa
        kandidatService.updateLocalKands();

        Optional<Par> parOpt = parService.find(Long.parseLong(parId));
        Optional<ZaposlenikSC> zaposelnikOpt = zaposlenikSCService.findBykorisnickoIme(username);

        if (parOpt.isPresent() && zaposelnikOpt.isPresent()) {
            Par par = parOpt.get();
            ZaposlenikSC zaposlenik = zaposelnikOpt.get();

            par.setOdobren(odobren);
            par.setZaposlenikSC(zaposlenik);

            parService.save(par);

            return ResponseEntity.ok(new ParDTO(par));
        }

        //"Par nije pronadjen"
        return ResponseEntity.notFound().build();
    }
}
