package progi.projekt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.KandidatDTO;
import progi.projekt.dto.OglasDTO;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Lajk;
import progi.projekt.model.Oglas;
import progi.projekt.service.KandidatService;
import progi.projekt.service.LajkService;
import progi.projekt.service.OglasService;
import progi.projekt.service.util.Key;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/oglas")
public class OglasController {


    private OglasService oglasService;
    private LajkService lajkService;
    private KandidatService kandidatService;

    public OglasController(OglasService oglasService, LajkService lajkService, KandidatService kandidatService) {
        this.oglasService = oglasService;
        this.lajkService = lajkService;
        this.kandidatService = kandidatService;
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

    @GetMapping(value = "/listKandidati")
    public List<KandidatDTO> listKandidati(@RequestParam(value = "oglas_id") String oglasId) {
        Optional<Oglas> oglasOpt = oglasService.findById(oglasId);
        ArrayList<KandidatDTO> kandidatiDTO = new ArrayList<KandidatDTO>();

        if (oglasOpt.isPresent()){
            Oglas oglas = oglasOpt.get();
            UUID studentID = oglas.getStudent().getId();
            List<Lajk> lajkovi = lajkService.listAll();
            for (Lajk lajk : lajkovi) {
                if (lajk.getLajkId().getOglas().getId() == oglas.getId()) {
                    Optional<Integer> ocjenaOptional = Optional.ofNullable(lajk.getOcjena());
                    Kandidat tmp = kandidatService.kandidatParaOglasa(oglas, lajk.getLajkId().getOglas()).get();
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
            return kandidatiDTO;
        }

        else {
            return new ArrayList<KandidatDTO>();
        }
    }
}
