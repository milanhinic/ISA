package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.PredFilm;
import packages.enumerations.PredFilmTip;

public interface PredFilmInterface {
	
	public PredFilm getPredFilm(Long id);
	
	public PredFilm addPredFilm(PredFilm predFilm);
	
	public Page<PredFilm> getPredFilmList(PredFilmTip tip, Pageable pageable);
	
	public ArrayList<PredFilm> getAllPredFilms();
	
	public Long getRowCount(PredFilmTip tip);
	
	public ArrayList<PredFilm> getAllPredFilmsByTip(PredFilmTip tip);
	
	public Double getProjectionScore(PredFilm predFilm);
	
	public Double getAverageProjectionScore(PredFilmTip tip);

}
