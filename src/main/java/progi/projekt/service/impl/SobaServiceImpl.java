package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Soba;
import progi.projekt.service.SobaService;

import java.util.UUID;

@Service
public class SobaServiceImpl implements SobaService {
	@Override
	public Soba getByStudentId(UUID id) {
		return new Soba();
	}
}
