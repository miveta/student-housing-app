package progi.projekt.service;

import java.util.UUID;
import progi.projekt.model.Soba;

public interface SobaService {
	Soba getByStudentId(UUID id);
}
