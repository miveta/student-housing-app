package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Grad;
import progi.projekt.model.Soba;
import progi.projekt.repository.GradRepository;
import progi.projekt.repository.SobaRepository;
import progi.projekt.service.SobaService;

import java.util.List;
import java.util.UUID;

@Service
public class SobaServiceImpl implements SobaService {

	private SobaRepository sobaRepository;


	private GradRepository gradRepository;

	public List<Grad> findAllGrad() {
		return gradRepository.findAll();
	}

	public SobaServiceImpl(SobaRepository sobaRepository, GradRepository gradRepository) {
		this.sobaRepository = sobaRepository;
		this.gradRepository = gradRepository;
	}

	@Override
	public Soba getByStudentId(UUID id) {
		//todo
		return new Soba();
	}

	@Override
	public Soba getById(UUID id) {
		return sobaRepository.findById(id).get();
	}
}
