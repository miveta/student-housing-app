package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.repository.ParRepository;
import progi.projekt.service.ParService;

import java.util.List;

@Service
public class ParServiceImpl implements ParService {
	@Autowired
	private ParRepository parRepo;

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
	public boolean oglasiSuAKTIVAN(Par par) {
		boolean prviJeAKTIVAN = par.getOglas1().getStatus().getStatus() == StatusOglasaEnum.AKTIVAN;
		boolean drugiJeAKTIVAN = par.getOglas2().getStatus().getStatus() == StatusOglasaEnum.AKTIVAN;
		return prviJeAKTIVAN && drugiJeAKTIVAN ? true : false;
	}

	@Override
	public void rezervirajOglasePara(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.getStatus().setStatus(StatusOglasaEnum.CEKA);
		//oglas1.obavijestiService.dodajPotvrdiZamjenuObavijest();

		oglas2.getStatus().setStatus(StatusOglasaEnum.CEKA);
		//oglas2.obavijestiService.dodajPotvrdiZamjenuObavijest();

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
}
