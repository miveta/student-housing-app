package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Soba;
import progi.projekt.repository.SobaRepository;
import progi.projekt.service.SobaService;

import java.util.UUID;

@Service
public class SobaServiceImpl implements SobaService {

	private SobaRepository sobaRepository;

	public SobaServiceImpl(SobaRepository sobaRepository) {
		this.sobaRepository = sobaRepository;
	}

	@Override
	public Soba getByStudentId(UUID id) {
		//todo
		return new Soba();
	}

	@Override
	public Soba getById(Integer sobaId) {
		return sobaRepository.getByIdSoba(sobaId);
	}
}
