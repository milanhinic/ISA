package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.PredFilm;
import packages.enumerations.PredFilmTip;
import packages.repositories.PredFilmRepository;
import packages.repositories.RezervacijaRepository;
import packages.serviceInterfaces.PredFilmInterface;

@Service
public class PredFilmService implements PredFilmInterface{

	@Autowired
	private PredFilmRepository pfr;
	
	@Autowired
	RezervacijaRepository rr;
	
	@Override
	public PredFilm getPredFilm(Long id) {
		// TODO Auto-generated method stub
		return pfr.findOne(id);
	}

	@Override
	public PredFilm addPredFilm(PredFilm predFilm) {
		// TODO Auto-generated method stub
		return pfr.save(predFilm);
	}

	@Override
	public Page<PredFilm> getPredFilmList(PredFilmTip tip, Pageable pageable) {
		// TODO Auto-generated method stub
		return pfr.findByTip(tip, pageable);
	}

	@Override
	public ArrayList<PredFilm> getAllPredFilms() {
		// TODO Auto-generated method stub
		return (ArrayList<PredFilm>) pfr.findAll();
	}

	@Override
	public Long getRowCount(PredFilmTip tip) {
		// TODO Auto-generated method stub
		return pfr.countByTip(tip);
	}

	@Override
	public ArrayList<PredFilm> getAllPredFilmsByTip(PredFilmTip tip) {
		// TODO Auto-generated method stub
		return pfr.findByTip(tip);
	}

	@Override
	public Double getProjectionScore(PredFilm predFilm) {
		// TODO Auto-generated method stub
		Long ukupno = rr.getProjectionScores(predFilm);
		Long count = rr.countProjectionScores(predFilm);
	
		return (double)ukupno/count;
	}

	@Override
	public Double getAverageProjectionScore(PredFilmTip tip) {
		// TODO Auto-generated method stub
		Long ukupno = rr.getProjectionScoresI(tip);
		Long count = rr.countProjectionScoresI(tip);
	
		return (double)ukupno/count;
	}
}
