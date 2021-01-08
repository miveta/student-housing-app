package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.repository.OglasRepository;
import progi.projekt.repository.ParRepository;
import progi.projekt.service.KandidatService;
import progi.projekt.service.ParService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParServiceImpl implements ParService {

	private ParRepository parRepo;
	private KandidatService kandidatService;
	private OglasRepository oglasRepo;

	public ParServiceImpl(ParRepository parRepo, KandidatService kandidatService, OglasRepository oglasRepo) {
		this.parRepo = parRepo;
		this.kandidatService = kandidatService;
		this.oglasRepo = oglasRepo;
	}

	@Override
	public List<Par> listAll() {
		return parRepo.findAll();
	}

	@Override
	public List<Par> listAll(Oglas oglas) {
		return parRepo.findAllByOglas1OrOglas2(oglas,oglas);
	}

	@Override
	public Integer ObostranaOcjena(Integer ocjena1, Integer ocjena2) {
		return ocjena1 + ocjena2;
	}

	@Override
	public boolean parSadrziOglas(Par par, Oglas oglas) {
		return par.getOglas1() == oglas || par.getOglas2() == oglas;
	}

	@Override
	public void rezervirajOglasePara(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.setStatusOglasa(StatusOglasaEnum.CEKA);
		oglas2.setStatusOglasa(StatusOglasaEnum.CEKA);

		oglasRepo.save(oglas1);
		oglasRepo.save(oglas2);

		//par.setCeka(true);

		//todo:
		//oglas1.obavijestiService.dodajPotvrdiZamjenuObavijest();
		//oglas2.obavijestiService.dodajPotvrdiZamjenuObavijest();
	}

	@Override
	public void potvrdiOglasePara(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.setStatusOglasa(StatusOglasaEnum.POTVRDEN);
		oglas2.setStatusOglasa(StatusOglasaEnum.POTVRDEN);

		oglasRepo.save(oglas1);
		oglasRepo.save(oglas2);

		//par.setCeka(true);

		//todo:
		//oglas1.obavijestiService.dodajPotvrdiZamjenuObavijest();
		//oglas2.obavijestiService.dodajPotvrdiZamjenuObavijest();
	}

	@Override
	public void vratiOglaseParaNaAKTIVAN(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.setStatusOglasa(StatusOglasaEnum.AKTIVAN);
		oglas2.setStatusOglasa(StatusOglasaEnum.AKTIVAN);

		oglasRepo.save(oglas1);
		oglasRepo.save(oglas2);
	}

	@Override
	public void save(Par par) {
		List<Par> parovi = listAll();
		for (Par parTmp : parovi){
			if (parTmp.getOglas1() == par.getOglas1() && parTmp.getOglas2() == par.getOglas2()) return;
			if (parTmp.getOglas1() == par.getOglas2() && parTmp.getOglas2() == par.getOglas1()) return;
		}
		if (par.getOglas1() == par.getOglas2()) return;
		parRepo.save(par);
	}

	@Override
	public boolean ifObaAKTIVAN(Par par) {
		var statusPrvogOglasa = par.getOglas1().getStatusOglasa();
		var statusDrugogOgalsa = par.getOglas2().getStatusOglasa();

		return statusPrvogOglasa == StatusOglasaEnum.AKTIVAN && statusDrugogOgalsa == StatusOglasaEnum.AKTIVAN;
	}

	@Override
	public boolean ifObaCEKA(Par par) {
		var statusPrvogOglasa = par.getOglas1().getStatusOglasa();
		var statusDrugogOgalsa = par.getOglas2().getStatusOglasa();

		return statusPrvogOglasa == StatusOglasaEnum.CEKA && statusDrugogOgalsa == StatusOglasaEnum.CEKA;
	}

	@Override
	public Integer TrostranaOcjena(Integer ocjenaAB, Integer ocjenaBC, Integer ocjenaCA) {
		return (ocjenaAB + ocjenaBC + ocjenaCA) * 2/3;
	}

	//primi par i vrati oglas3, gdje je oglas 3 treci oglas lanaca
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

	//primi oglas1 i vrati listu [oglas1, oglas2, oglas3], gdje oglasi 1-3 cine lanac
	@Override
	public List<Oglas> pripadniOglasiLanca(Oglas oglas1) {
		Par parAB = pripadniParOglasa(oglas1).get();
		Oglas oglas2;
		if (parAB.getOglas1() == oglas1){
			oglas2 = parAB.getOglas2();
		} else {
			oglas2 = parAB.getOglas1();
		}
		Oglas oglas3 = pronadjiTreciOglasIzLanca(parAB).get();

		List<Oglas> lanacOglasi = new ArrayList<>();
		lanacOglasi.add(oglas1);
		lanacOglasi.add(oglas2);
		lanacOglasi.add(oglas3);

		return lanacOglasi;
	}

	//primi oglas1 i vrati listu [parAB, parBC, parCA] koji zajedno cine lanac
	@Override
	public List<Par> pripadniParoviLanca(Oglas oglas1) {
		List<Oglas> lanacOglasi = pripadniOglasiLanca(oglas1);

		Par parAB = pripadniParAB(lanacOglasi.get(0), lanacOglasi.get(1)).get();
		Par parBC = pripadniParAB(lanacOglasi.get(1), lanacOglasi.get(2)).get();
		Par parCA = pripadniParAB(lanacOglasi.get(2), lanacOglasi.get(0)).get();

		List<Par> lanacParovi = new ArrayList<>();

		lanacParovi.add(parAB);
		lanacParovi.add(parBC);
		lanacParovi.add(parCA);

		return lanacParovi;
	}

	@Override
	public Optional<Par> pripadniParOglasa (Oglas oglas) {
		Optional<Par> par = Optional.empty();

		List<Par> paroviC = listAll();
		for (Par parC : paroviC){
			if (!parC.getLanac()) {
				if (parSadrziOglas(parC, oglas)){
					par = Optional.of(parC);
					break;
				}
			}
			else {
				if (parC.getOglas1() == oglas){
					par = Optional.of(parC);
					break;
				}
			}

		}
		return par;
	}

	//note: ova metoda je predvidjena za rad sa lancima pa je
	// redoslijed poslanih argumenata je bitan
	@Override
	public Optional<Par> pripadniParAB(Oglas oglasA, Oglas oglasB) {
		Optional<Par> par = Optional.empty();

		List<Par> paroviC = listAll();
		for (Par parC : paroviC){
			if (parC.getOglas1() == oglasA && parC.getOglas2() == oglasB){
				par = Optional.of(parC);
				break;
			}
		}
		return par;
	}

	@Override
	public Optional<Kandidat> pripadniKandidatPara(Par par) {
		Optional<Kandidat> kandidat = kandidatService.kandidatParaOglasa(par.getOglas1(), par.getOglas2());
		return kandidat;
	}


	@Override
	public void ponistiParoveOglasa(Oglas oglas) {
		List<Par> parovi = listAll();
		for (Par par : parovi){
			if (!par.getDone()) {
				par.setIgnore(true);
				save(par);
			}

		}
	}
}
