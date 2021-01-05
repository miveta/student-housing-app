package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.repository.ParRepository;
import progi.projekt.service.KandidatService;
import progi.projekt.service.ParService;

import java.util.List;
import java.util.Optional;

@Service
public class ParServiceImpl implements ParService {

	private ParRepository parRepo;
	private KandidatService kandidatService;

	public ParServiceImpl(ParRepository parRepo, KandidatService kandidatService) {
		this.parRepo = parRepo;
		this.kandidatService = kandidatService;
	}

	@Override
	public List<Par> listAll() {
		return parRepo.findAll();
	}

	@Override
	public Integer ObostranaOcjena(Integer ocjena1, Integer ocjena2) {
		return ocjena1 + ocjena2;
	}

	@Override
	public boolean parSadrziOglas(Par par, Oglas oglas) {
		return par.getOglas1() == oglas || par.getOglas2() == oglas ? true : false;
	}

	@Override
	public void rezervirajOglasePara(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.getStatus().setStatus(StatusOglasaEnum.CEKA);
		oglas2.getStatus().setStatus(StatusOglasaEnum.CEKA);

		//par.setCeka(true);

		//todo:
		//oglas1.obavijestiService.dodajPotvrdiZamjenuObavijest();
		//oglas2.obavijestiService.dodajPotvrdiZamjenuObavijest();
	}

	@Override
	public void potvrdiOglasePara(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.getStatus().setStatus(StatusOglasaEnum.POTVRDEN);
		oglas2.getStatus().setStatus(StatusOglasaEnum.POTVRDEN);

		//par.setCeka(true);

		//todo:
		//oglas1.obavijestiService.dodajPotvrdiZamjenuObavijest();
		//oglas2.obavijestiService.dodajPotvrdiZamjenuObavijest();
	}

	@Override
	public void vratiOglaseParaNaAKTIVAN(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.getStatus().setStatus(StatusOglasaEnum.AKTIVAN);
		oglas2.getStatus().setStatus(StatusOglasaEnum.AKTIVAN);
	}

	@Override
	public void save(Par par) {
		parRepo.save(par);
	}

	@Override
	public boolean ifObaAKTIVAN(Par par) {
		var statusPrvogOglasa = par.getOglas1().getStatus().getStatus();
		var statusDrugogOgalsa = par.getOglas2().getStatus().getStatus();

		return statusPrvogOglasa == StatusOglasaEnum.AKTIVAN && statusDrugogOgalsa == StatusOglasaEnum.AKTIVAN;
	}

	@Override
	public Integer TrostranaOcjena(Integer ocjenaAB, Integer ocjenaBC, Integer ocjenaCA) {
		return (ocjenaAB + ocjenaBC + ocjenaCA) * 2/3;
	}

	@Override
	public Optional<Oglas> pronadjiTreciOglasIzLanca(Par par) {
		List<Par> paroviC = listAll();
		Optional<Oglas> oglas3Opt = Optional.empty();
		for (Par parC : paroviC){
			if 		(parSadrziOglas(parC, par.getOglas2()) &&
					!parSadrziOglas(parC, par.getOglas1())){
				if (parC.getOglas1() == par.getOglas2()){
					oglas3Opt = Optional.ofNullable(parC.getOglas2());
				} else {
					oglas3Opt = Optional.ofNullable(parC.getOglas1());
				}
			}
		}
		return oglas3Opt;
	}

	@Override
	public Optional<Par> pripadniParOglasa(Oglas oglas) {
		Optional<Par> par = Optional.empty();

		List<Par> paroviC = listAll();
		for (Par parC : paroviC){
			if (parSadrziOglas(parC, oglas)){
				par = Optional.of(parC);
				break;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Kandidat> pripadniKandidatPara(Par par) {
		Optional<Kandidat> kandidat = Optional.empty();

		return Optional.empty();
	}
}
