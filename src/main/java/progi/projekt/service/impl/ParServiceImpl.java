package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Par;
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
}
