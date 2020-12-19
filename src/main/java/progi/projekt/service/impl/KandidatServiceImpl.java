package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Kandidat;
import progi.projekt.repository.KandidatRepository;
import progi.projekt.service.KandidatService;

import java.util.List;

@Service
public class KandidatServiceImpl implements KandidatService {
	@Autowired
	private KandidatRepository kandidatRepo;

	@Override
	public List<Kandidat> listAll() {
		return kandidatRepo.findAll();
	}
}
