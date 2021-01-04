package progi.projekt.service;

import progi.projekt.model.Soba;

import java.util.UUID;

public interface SobaService {
	Soba getByStudentId(UUID id);

	Soba getById(UUID id);
}
