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
    private static final int MILISEC_IZMEDJU_POZIVA = 2 * 1000; //5 s

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
        List<Oglas> oglasi = oglasService.listAll();
        oglasi = oglasi.stream().filter(o -> o.getStatusOglasa() == StatusOglasaEnum.AKTIVAN || o.getStatusOglasa() == StatusOglasaEnum.CEKA).collect(Collectors.toList());
        return oglasi.stream().map(OglasDTO::new).collect(Collectors.toList());
    }


    @GetMapping("/getoglas")
    public ResponseEntity<?> getOglas(@RequestParam(value = "oglas_id") String oglasId) {
        Optional<Oglas> optionalOglas = oglasService.findById(oglasId);
        if(optionalOglas.isPresent()) return ResponseEntity.ok(new OglasDTO(optionalOglas.get()));
        return ResponseEntity.ok().build();
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

        matchingService.resetirajOglas(aktivniOglas.getId());

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

        Thread kandidatiThread = new Thread(() -> {
            matchingService.resetirajOglas(arhiviraniOglas.getId());
            try {
                Thread.sleep(MILISEC_IZMEDJU_POZIVA);
            } catch (InterruptedException e) {
                System.err.println("Scheduled matching execution interrupted");
            }
            matchingService.kandidatiFun();
            try {
                Thread.sleep(MILISEC_IZMEDJU_POZIVA);
            } catch (InterruptedException e) {
                System.err.println("Scheduled matching execution interrupted");
            }
            matchingService.parFun();
        });
        final boolean THREADS = false;
        if (THREADS){
            kandidatiThread.start();
        } else {
            matchingService.resetirajOglas(arhiviraniOglas.getId());
            matchingService.kandidatiFun();
            matchingService.parFun();
        }

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

    //http://localhost:8080/oglas/listKandidati?oglas_id=cba3fff8-3568-4142-84f5-7490945b657c
    @GetMapping(value = "/listKandidati")
    public ArrayList<KandidatDTO> listKandidati(@RequestParam(value = "oglas_id") String oglasId) {
        //force update oglasa unutar svakog studenta
        //updateOglasInStudents(oglasi);

        //force update kandidata unutar svakog oglasa
        kandidatService.updateLocalKands();

        Optional<Oglas> oglasOpt = oglasService.findById(oglasId);

        if (oglasOpt.isEmpty()) return new ArrayList<>();


        Oglas oglas = oglasOpt.get();
        ArrayList<KandidatDTO> kandidatiDTO = new ArrayList<>();
        List<Lajk> lajkovi = lajkService.listAll();

        long brojOcjenaPredanogOglasa =
                lajkovi.stream().filter(lajk -> lajk.getLajkId().getOglas().getId() == oglas.getId()).count();


        for (Kandidat kand : oglas.getKandidati()) {

            KandidatDTO tmpDTO = new KandidatDTO(kand);

            if (brojOcjenaPredanogOglasa > 0) {
                for (Lajk lajk : lajkovi) {
                    if (lajk.getLajkId().getOglas().equals(oglas)) {
                        Optional<Integer> ocjenaOptional = Optional.of(lajk.getOcjena());
                        Oglas drugiOglas = lajk.getLajkId().getStudent().getAktivniOglas();

                        Optional<Kandidat> tmpOpt = kandidatService.kandidatParaOglasa(oglas, drugiOglas);
                        if (tmpOpt.isPresent()) {
                            Kandidat tmp = tmpOpt.get();

                            //KandOcjena je ocjena koju je kandidat dao nasem oglasu
                            ocjenaOptional.ifPresentOrElse(
                                    tmpDTO::setKandOcjena,
                                    () ->
                                    {
                                        //ako ocjena jos nije unesena upisujemo -1
                                        tmpDTO.setKandOcjena(-1);
                                    });
                        }
                    }
                }

            } else {
                tmpDTO.setKandOcjena(-1);
            }

            kandidatiDTO.add(tmpDTO);
        }

        return kandidatiDTO;

    }

    //http://localhost:8080/oglas/listParovi?student_username=stud1
    @GetMapping(value = "/listParovi")
    public List<ParDTO> listParovi(@RequestParam(value = "student_username") String username) {
        //note: napravio sam ParDTO jer ako stavim Par u listu, toString je beskonacan jer oglas ima referencu na
        // studenta koji opet ima referencu na oglas. Ista stvar sa domovima
        // - holik

        //force update kandidata unutar svakog oglasa
        kandidatService.updateLocalKands();

        matchingService.matchFun();

        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        if (optionalStudent.isEmpty()) return new ArrayList<>();

        Student student = optionalStudent.get();
        Oglas oglas = student.getCekaOglas();

        if (oglas == null) return new ArrayList<>();

        List<Par> parovi = parService.pripadniParoviOglasa(oglas);
        ArrayList<ParDTO> paroviOglasa = new ArrayList<>();
        for (Par par : parovi) {
            if (!par.getLanac()) {
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

        }
        return paroviOglasa;
    }

    @GetMapping(value = "/listParoviWithFlags")
    public List<ParDTO> listParoviWithFlags(@RequestParam(value = "done") Boolean done) {
        //note: napravio sam ParDTO jer ako stavim Par u listu, toString je beskonacan jer oglas ima referencu na
        // studenta koji opet ima referencu na oglas. Ista stvar sa domovima
        // - holik

        //force update kandidata unutar svakog oglasa
        kandidatService.updateLocalKands();

        ArrayList<ParDTO> parovi = new ArrayList<>();

        for (Par par : parService.listAll()) {
            if (par.getDone() == done) {
                ParDTO parDTO = new ParDTO(par);
                parovi.add(parDTO);
            }
        }
        return parovi;
    }


    public void updateOglasInStudents(List<Oglas> oglasi) {
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
    }

    //http://localhost:8080/oglas/updatePar?par_id=35&odobren=true&zaposlenikKorisnickoIme=stef567
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

        if (!odobren) par.setIgnore(true);
        else {
            if (prvi) par.setPrihvatioPrvi(true);
            if (drugi) par.setPrihvatioDrugi(true);

            if (par.getPrihvatioPrvi() != null && par.getPrihvatioDrugi() != null)
                par.setDone(par.getPrihvatioPrvi() && par.getPrihvatioDrugi());
        }

        parService.update(par);
        matchingService.confirmFun();
        return ResponseEntity.ok(par);
    }

    @PostMapping(value = "/updateParSC")
    public ResponseEntity<?> updateParSc(@RequestParam(value = "par_id") String parId,
                                         @RequestParam(value = "odobren") Boolean odobren,
                                         @RequestParam(value = "zaposlenikKorisnickoIme") String username) {

        List<Oglas> oglasi = oglasService.listAll();

        //force update oglasa unutar svakog studenta
        //updateOglasInStudents(oglasi);

        //force update kandidata unutar svakog oglasa
        kandidatService.updateLocalKands();

        Optional<Par> parOpt = parService.find(Long.parseLong(parId));
        Optional<ZaposlenikSC> zaposelnikOpt = zaposlenikSCService.findBykorisnickoIme(username);

        if (parOpt.isPresent() && zaposelnikOpt.isPresent()) {
            Par par = parOpt.get();
            ZaposlenikSC zaposlenik = zaposelnikOpt.get();

            par.setOdobren(odobren);
            par.setZaposlenikSC(zaposlenik);

            parService.update(par);

            return ResponseEntity.ok(new ParDTO(par));
        }

        //"Par nije pronadjen"
        return ResponseEntity.notFound().build();
    }
}
